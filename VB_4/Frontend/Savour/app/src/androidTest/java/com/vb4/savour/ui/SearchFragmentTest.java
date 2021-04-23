package com.vb4.savour.ui;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.core.util.Consumer;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.vb4.savour.R;
import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.model.SearchRequest;
import com.vb4.savour.ui.main.MainActivity;
import com.vb4.savour.ui.search.SearchFragment;
import com.vb4.savour.ui.search.SearchViewModel;

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
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.vb4.savour.ui.SavourTestUtil.withRecyclerView;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SearchFragmentTest {
    @Mock
    public SearchViewModel mSearchViewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setup() {

        MockitoAnnotations.openMocks(this);
        when(mSearchViewModel.getRecipeList(any(SearchRequest.class)))
                .thenReturn(new MutableLiveData<>(AsyncData.success(SavourTestUtil.fakeRecipes())));
    }

    @After
    public void teardown() {
        SavourTestUtil.clearAuthToken(activityRule);
    }

    @Test
    public void testPreformSearch() throws InterruptedException {
        SavourTestUtil.login(activityRule);

        SearchRequest search = new SearchRequest();

        onView(withId(R.id.navigation_search)).perform(click());
        onView(withId(R.id.action_search)).perform(click());
        onView(withId(R.id.action_search))
                .perform(typeText("a"), closeSoftKeyboard());
        search.query = "a";

        Consumer<Fragment> onInject = fragment -> ((SearchFragment)fragment).fetch(search);


        try {
            SavourTestUtil.injectViewModel(activityRule, "searchViewModel", mSearchViewModel, onInject);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Couldn't inject ViewModel");
        }

        Thread.sleep(500); // Buffer for items to show

        onView(withRecyclerView(R.id.recycler_view_search).atPositionOnView(0, R.id.recipe_card_name))
                .check(matches(withText("Recipe 0")));
        onView(withRecyclerView(R.id.recycler_view_search).atPositionOnView(1, R.id.recipe_card_name))
                .check(matches(withText("Recipe 1")));
    }
}
