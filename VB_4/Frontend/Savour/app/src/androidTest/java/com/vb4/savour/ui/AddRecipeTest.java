package com.vb4.savour.ui;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.core.util.Consumer;
import androidx.lifecycle.MutableLiveData;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.client.SavourClient;
import com.vb4.savour.data.model.AddRecipeRequest;
import com.vb4.savour.data.model.Recipe;
import com.vb4.savour.data.model.RecipeWithoutId;
import com.vb4.savour.ui.addrecipe.AddRecipeActivity;
import com.vb4.savour.ui.addrecipe.AddRecipeViewModel;
import com.vb4.savour.ui.profile.ProfileFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddRecipeTest {
    @Mock
    public AddRecipeViewModel mViewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    static {
        SavourClient.initialize(ApplicationProvider.getApplicationContext());
    }

    @Rule
    public ActivityScenarioRule<AddRecipeActivity> addRecipeRule =
            new ActivityScenarioRule<>(AddRecipeActivity.class);


    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Setup mock add recipe
        when(mViewModel.addRecipe())
                .thenReturn(new MutableLiveData<>(AsyncData.success(SavourTestUtil.fakeRecipes()[0])));
    }

    @Test
    public void testAddRecipe() throws InterruptedException {
        RecipeWithoutId fakeAddRecipe = new RecipeWithoutId();
        fakeAddRecipe.name = "Recipe Fake";
        fakeAddRecipe.timeHours = 6;
        fakeAddRecipe.timeMinutes = 7;
        fakeAddRecipe.timeSeconds = 8;
        fakeAddRecipe.cost = 1.65;
        fakeAddRecipe.category = "Breakfast";
        fakeAddRecipe.mediaUrl = "https://i.pinimg.com/originals/ea/09/c9/ea09c92cb8af15284a6fd1953de93b53.jpg";

        AddRecipeRequest addRecipeRequest = new AddRecipeRequest();
        addRecipeRequest.recipe = fakeAddRecipe;
        addRecipeRequest.ingredients = SavourTestUtil.fakeIngredients();

        Consumer<Activity> onInject = activity -> ((AddRecipeActivity)activity).addRecipe();

        try {
            SavourTestUtil.injectViewModelActivity(addRecipeRule, "mViewModel", mViewModel, onInject);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Couldn't inject ViewModel");
        }

        Thread.sleep(500); // Buffer for items to show

        Instrumentation.ActivityResult result = addRecipeRule.getScenario().getResult();

        assertEquals(Activity.RESULT_OK, result.getResultCode());

        Intent intent = result.getResultData();

        int recipeId = intent.getIntExtra(ProfileFragment.EXTRA_RECIPE_ID, -1);

        assertNotEquals(-1, recipeId);
    }

}