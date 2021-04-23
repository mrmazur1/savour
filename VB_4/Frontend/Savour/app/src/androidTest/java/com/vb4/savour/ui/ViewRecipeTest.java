package com.vb4.savour.ui;

import android.app.Activity;
import android.content.Intent;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.core.util.Consumer;
import androidx.lifecycle.MutableLiveData;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.vb4.savour.R;
import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.client.SavourClient;
import com.vb4.savour.data.model.ViewRecipeModel;
import com.vb4.savour.ui.viewrecipe.ViewRecipeActivity;
import com.vb4.savour.ui.viewrecipe.ViewRecipeViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ViewRecipeTest {
    @Mock
    public ViewRecipeViewModel mViewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    static {
        SavourClient.initialize(ApplicationProvider.getApplicationContext());
        SavourClient.getInstance().testing = true;
    }

    @Rule
    public ActivityScenarioRule<ViewRecipeActivity> viewRecipeScenario =
            new ActivityScenarioRule<>(makeViewRecipeIntent());

    /**
     * Creates the initial Intent that is passed to ViewRecipe
     * @return the Intent for ViewRecipe
     */
    private static Intent makeViewRecipeIntent() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ViewRecipeActivity.class);
        intent.putExtra(ViewRecipeActivity.RECIPE_ID_EXTRA_KEY, 1);
        return intent;
    }

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Setup mock view recipe

        ViewRecipeModel viewRecipeModel = new ViewRecipeModel();
        viewRecipeModel.recipe = SavourTestUtil.fakeRecipes()[0];
        viewRecipeModel.ingredients = SavourTestUtil.fakeIngredients();

        when(mViewModel.getRecipe(anyInt()))
                .thenReturn(new MutableLiveData<>(AsyncData.success(viewRecipeModel)));
    }

    @Test
    public void testViewRecipeCard() throws InterruptedException {
        Consumer<Activity> onInject = activity -> ((ViewRecipeActivity)activity).fetch();

        try {
            SavourTestUtil.injectViewModelActivity(viewRecipeScenario, "mViewModel", mViewModel, onInject);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Couldn't inject ViewModel");
        }

        Thread.sleep(500); // Buffer for items to show

        onView(withId(R.id.text_view_view_recipe_name)).check(matches(withText("Recipe 0")));
        onView(withId(R.id.text_view_view_recipe_category)).check(matches(withText("Breakfast")));
        onView(withId(R.id.text_view_view_recipe_cost)).check(matches(withText("$1.65")));
        onView(withId(R.id.text_view_view_recipe_cooking_time)).check(matches(withText("7m8s")));
    }
}


