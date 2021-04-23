package com.vb4.savour.ui;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.core.util.Consumer;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.vb4.savour.R;
import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.model.GroceryListModelPiece;
import com.vb4.savour.data.model.Ingredient;
import com.vb4.savour.data.model.Recipe;
import com.vb4.savour.ui.grocerylist.GroceryListFragment;
import com.vb4.savour.ui.grocerylist.GroceryListViewModel;
import com.vb4.savour.ui.main.MainActivity;

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
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.vb4.savour.ui.SavourTestUtil.withRecyclerView;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GroceryListFragmentTest {
    @Mock
    public GroceryListViewModel mGroceryListViewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    private final LiveData<AsyncData<GroceryListModelPiece[]>> fakeSuccess;

    public GroceryListFragmentTest() {
        GroceryListModelPiece[] pieces = {
                new GroceryListModelPiece(),
                new GroceryListModelPiece()
        };

        Recipe[] recipes = SavourTestUtil.fakeRecipes();
        Ingredient[] ingredients = SavourTestUtil.fakeIngredients();

        for (int i = 0; i < pieces.length; i++) {
            pieces[i].recipe = recipes[i];
            pieces[i].ingredients = ingredients;
        }

        fakeSuccess = new MutableLiveData<>(AsyncData.success(pieces));
    }

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Setup mock discover
        when(mGroceryListViewModel.getGroceryList())
                .thenReturn(fakeSuccess);
    }

    @After
    public void teardown() {
        SavourTestUtil.clearAuthToken(activityRule);
    }

    @Test
    public void testHeaderDisplays() throws InterruptedException {
        SavourTestUtil.login(activityRule);

        Consumer<Fragment> onInject = fragment -> ((GroceryListFragment)fragment).fetch();

        onView(withId(R.id.navigation_grocery_list)).perform(click());

        try {
            SavourTestUtil.injectViewModel(activityRule, "groceryListViewModel", mGroceryListViewModel, onInject);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Couldn't inject ViewModel");
        }

        Thread.sleep(500); // Buffer for items to show

        onView(withRecyclerView(R.id.recycler_view_grocery_list).atPositionOnView(0, R.id.text_view_header_recipe_name))
                .check(matches(withText("Recipe 0")));
        onView(withRecyclerView(R.id.recycler_view_grocery_list).atPositionOnView(1, R.id.text_view_item_ingredient_name))
                .check(matches(withText("Ingredient 0")));
        onView(withRecyclerView(R.id.recycler_view_grocery_list).atPositionOnView(2, R.id.text_view_item_ingredient_name))
                .check(matches(withText("Ingredient 1")));
        onView(withRecyclerView(R.id.recycler_view_grocery_list).atPositionOnView(3, R.id.text_view_header_recipe_name))
                .check(matches(withText("Recipe 1")));
    }
}
