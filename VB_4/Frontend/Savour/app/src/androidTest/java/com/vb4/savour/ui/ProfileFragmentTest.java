package com.vb4.savour.ui;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.core.util.Consumer;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.vb4.savour.R;
import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.model.GetProfileResponse;
import com.vb4.savour.data.model.Recipe;
import com.vb4.savour.ui.main.MainActivity;
import com.vb4.savour.ui.profile.ProfileFragment;
import com.vb4.savour.ui.profile.ProfileViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.vb4.savour.ui.SavourTestUtil.withRecyclerView;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProfileFragmentTest {
    @Mock
    public ProfileViewModel mProfileViewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @After
    public void teardown() {
        SavourTestUtil.clearAuthToken(activityRule);
    }

    @Test
    public void testAddRecipeButtonPresent() {
        SavourTestUtil.login(activityRule);

        Consumer<Fragment> onInject = fragment -> ((ProfileFragment)fragment).getProfile();

        onView(withId(R.id.navigation_profile)).perform(click());

        GetProfileResponse canAddRecipe = new GetProfileResponse();
        canAddRecipe.favoriteRecipes = new Recipe[]{};
        canAddRecipe.recentlyViewed = new Recipe[]{};
        canAddRecipe.canAddRecipe = true;

        when(mProfileViewModel.getProfile())
                .thenReturn(new MutableLiveData<>(AsyncData.success(canAddRecipe)));

        try {
            SavourTestUtil.injectViewModel(activityRule, "profileViewModel", mProfileViewModel, onInject);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Couldn't inject ViewModel");
        }

        onView(withId(R.id.button_profile_add_recipe))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testAddRecipeButtonHidden() {
        SavourTestUtil.login(activityRule);

        Consumer<Fragment> onInject = fragment -> ((ProfileFragment)fragment).getProfile();

        onView(withId(R.id.navigation_profile)).perform(click());

        GetProfileResponse cannotAddRecipe = new GetProfileResponse();
        cannotAddRecipe.favoriteRecipes = new Recipe[]{};
        cannotAddRecipe.recentlyViewed = new Recipe[]{};
        cannotAddRecipe.canAddRecipe = false;

        when(mProfileViewModel.getProfile())
                .thenReturn(new MutableLiveData<>(AsyncData.success(cannotAddRecipe)));

        try {
            SavourTestUtil.injectViewModel(activityRule, "profileViewModel", mProfileViewModel, onInject);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Couldn't inject ViewModel");
        }

        onView(withId(R.id.button_profile_add_recipe))
                .check(matches(not(isDisplayed())));
    }

    @Test
    public void testRecentlyViewedDisplays() {
        SavourTestUtil.login(activityRule);

        Consumer<Fragment> onInject = fragment -> ((ProfileFragment)fragment).getProfile();

        onView(withId(R.id.navigation_profile)).perform(click());

        GetProfileResponse recentlyViewed = new GetProfileResponse();
        recentlyViewed.favoriteRecipes = new Recipe[]{};
        recentlyViewed.recentlyViewed = SavourTestUtil.fakeRecipes();
        recentlyViewed.canAddRecipe = false;

        when(mProfileViewModel.getProfile())
                .thenReturn(new MutableLiveData<>(AsyncData.success(recentlyViewed)));

        try {
            SavourTestUtil.injectViewModel(activityRule, "profileViewModel", mProfileViewModel, onInject);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Couldn't inject ViewModel");
        }

        onView(withRecyclerView(R.id.recycler_view_profile_recently_viewed).atPosition(0))
                .check(matches(isDisplayed()));
        onView(withRecyclerView(R.id.recycler_view_profile_recently_viewed).atPosition(1))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testFavoritesDisplays() {
        SavourTestUtil.login(activityRule);

        Consumer<Fragment> onInject = fragment -> ((ProfileFragment)fragment).getProfile();

        onView(withId(R.id.navigation_profile)).perform(click());

        GetProfileResponse favorites = new GetProfileResponse();
        favorites.favoriteRecipes = SavourTestUtil.fakeRecipes();
        favorites.recentlyViewed = new Recipe[]{};
        favorites.canAddRecipe = false;

        when(mProfileViewModel.getProfile())
                .thenReturn(new MutableLiveData<>(AsyncData.success(favorites)));

        try {
            SavourTestUtil.injectViewModel(activityRule, "profileViewModel", mProfileViewModel, onInject);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Couldn't inject ViewModel");
        }

        onView(withRecyclerView(R.id.recycler_view_profile_favorites).atPosition(0)).check(matches(isDisplayed()));
        onView(withRecyclerView(R.id.recycler_view_profile_favorites).atPosition(1)).check(matches(isDisplayed()));
    }

    @Test
    public void testLogoutButton() {
        SavourTestUtil.login(activityRule);

        onView(withId(R.id.navigation_profile)).perform(click());

        onView(withId(R.id.button_profile_logout)).perform(click());

        onView(withId(R.id.button_login_submit)).check(matches(isDisplayed()));
    }
}
