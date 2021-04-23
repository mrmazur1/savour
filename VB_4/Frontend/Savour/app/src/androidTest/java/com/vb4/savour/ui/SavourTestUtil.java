package com.vb4.savour.ui;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.core.util.Consumer;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.vb4.savour.R;
import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.client.SavourClient;
import com.vb4.savour.data.model.Ingredient;
import com.vb4.savour.data.model.CreateUserResponse;
import com.vb4.savour.data.model.Recipe;
import com.vb4.savour.data.model.SearchRequest;
import com.vb4.savour.data.model.User;
import com.vb4.savour.lib.RecyclerViewMatcher;
import com.vb4.savour.ui.login.LoginFragment;
import com.vb4.savour.ui.login.LoginViewModel;
import com.vb4.savour.ui.main.MainActivity;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Methods used to assist in making tests readable
 */
public class SavourTestUtil {
    /**
     * Returns an array of fake recipes.
     * @return array of fake recipes
     */
    public static Recipe[] fakeRecipes() {
        Recipe[] recipes = {
                new Recipe(),
                new Recipe()
        };

        for (int i = 0; i < recipes.length; i++) {
            Recipe r = recipes[i];
            r.name = "Recipe " + i;
            r.timeHours = 6;
            r.timeMinutes = 7;
            r.timeSeconds = 8;
            r.cost = 1.65;
            r.category = "Breakfast";
            r.id = i;
            r.mediaUrl = "https://i.pinimg.com/originals/ea/09/c9/ea09c92cb8af15284a6fd1953de93b53.jpg";
        }

        return recipes;
    }

    public static SearchRequest fakeSearchRequest() {
        SearchRequest s = new SearchRequest();
        s.query = "";
        s.category = "Breakfast";
        return s;
    }

    /**
     * Returns an array of fake ingredients.
     * @return array of fake ingredients
     */
    public static Ingredient[] fakeIngredients() {
        Ingredient[] ingredients = {
                new Ingredient(),
                new Ingredient()
        };

        for (int i = 0; i < ingredients.length; i++) {
            Ingredient ingredient = ingredients[i];
            ingredient.name = "Ingredient " + i;
            ingredient.amount = 14.0;
            ingredient.type = "SNCK";
            ingredient.unit = "cups";
        }

        return ingredients;
    }

    /**
     * Clear the authentication token saved when a successful login occurs
     * @param activityRule the rule activity of this test
     */
    public static void clearAuthToken(ActivityScenarioRule<MainActivity> activityRule) {
        activityRule.getScenario().onActivity(activity -> {
            String prefKey = activity.getResources().getString(R.string.preference_file_key);
            activity.getSharedPreferences(prefKey, Context.MODE_PRIVATE)
                    .edit()
                    .clear()
                    .commit();
        });
    }

    public static boolean loggedIn(ActivityScenarioRule<MainActivity> activityRule) {
        AtomicBoolean loggedIn = new AtomicBoolean(false);
        activityRule.getScenario().onActivity(activity -> {
            Fragment f = activity.getSupportFragmentManager()
                    .getPrimaryNavigationFragment()
                    .getChildFragmentManager()
                    .getFragments()
                    .get(0);

            loggedIn.set(!(f instanceof LoginFragment));
        });

        return loggedIn.get();
    }

    /**
     * Performs a successful login
     */
    public static void login(ActivityScenarioRule<MainActivity> activityRule) {
        if (SavourTestUtil.loggedIn(activityRule)) {
           return;
        }

        SavourClient.getInstance().testing = true;

        User success = new User();
        success.userId = 6;

        LoginViewModel mockedLoginViewModel = mock(LoginViewModel.class);

        when(mockedLoginViewModel.login(anyString(), anyString()))
                .thenReturn(new MutableLiveData<>(AsyncData.success(success)));

        try {
            SavourTestUtil.injectViewModel(activityRule, "mViewModel", mockedLoginViewModel, null);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

        onView(withId(R.id.edit_text_login_username))
                .perform(typeText("a"), closeSoftKeyboard());
        onView(withId(R.id.edit_text_login_password))
                .perform(typeText("a"), closeSoftKeyboard());
        onView(withId(R.id.button_login_submit)).perform(click());
    }

    /**
     * Injects a mocked {@link ViewModel} into the field of a {@link Fragment}.
     * @param activityRule the activity rule of the test
     * @param viewModelFieldName the name of the field of the {@link ViewModel} in the {@link Fragment}
     * @param mockedViewModel the mocked {@link ViewModel}
     * @param withFragment optional callback fired with the {@link Fragment} after injection
     * @throws Exception {@link ReflectiveOperationException},
     */
    public static void injectViewModel(
        ActivityScenarioRule<MainActivity> activityRule,
        String viewModelFieldName,
        ViewModel mockedViewModel,
        @Nullable Consumer<Fragment> withFragment
    ) throws Exception {
        AtomicReference<Exception> failed = new AtomicReference<>(null);

        activityRule.getScenario().onActivity(activity -> {
            Fragment f = activity.getSupportFragmentManager()
                    .getPrimaryNavigationFragment()
                    .getChildFragmentManager()
                    .getFragments()
                    .get(0);

            try {
                Field viewModelField = f.getClass().getDeclaredField(viewModelFieldName);
                viewModelField.setAccessible(true);
                viewModelField.set(f, mockedViewModel);

                if (withFragment != null) withFragment.accept(f);
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
                failed.set(e);
            }
        });

        if (failed.get() != null) {
            throw failed.get();
        }
    }

    /**
     * Injects a mocked {@link ViewModel} into the field of an {@link Activity}.
     * @param activityRule the activity rule of the test
     * @param viewModelFieldName the name of the field of the {@link ViewModel} in the {@link Activity}
     * @param mockedViewModel the mocked {@link ViewModel}
     * @param withActivity optional callback fired with the {@link Activity} after injection
     * @throws Exception {@link ReflectiveOperationException},
     */
    public static void injectViewModelActivity(
            ActivityScenarioRule<? extends Activity> activityRule,
            String viewModelFieldName,
            ViewModel mockedViewModel,
            @Nullable Consumer<Activity> withActivity
    ) throws Exception {
        AtomicReference<Exception> failed = new AtomicReference<>(null);

        activityRule.getScenario().onActivity(activity -> {
            try {
                Field viewModelField = activity.getClass().getDeclaredField(viewModelFieldName);
                viewModelField.setAccessible(true);
                viewModelField.set(activity, mockedViewModel);

                if (withActivity != null) withActivity.accept(activity);
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
                failed.set(e);
            }
        });

        if (failed.get() != null) {
            throw failed.get();
        }
    }

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }
}
