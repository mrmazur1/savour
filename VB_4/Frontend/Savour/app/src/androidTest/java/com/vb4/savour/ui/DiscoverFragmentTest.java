package com.vb4.savour.ui;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.core.util.Consumer;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.vb4.savour.R;
import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.ui.discover.DiscoverFragment;
import com.vb4.savour.ui.discover.DiscoverViewModel;
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
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.vb4.savour.ui.SavourTestUtil.withRecyclerView;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DiscoverFragmentTest {
    @Mock
    public DiscoverViewModel mDiscoverViewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);

        SavourTestUtil.clearAuthToken(activityRule);

        // Setup mock discover
        when(mDiscoverViewModel.getRecipeList())
                .thenReturn(new MutableLiveData<>(AsyncData.success(SavourTestUtil.fakeRecipes())));
    }

    @After
    public void teardown() {
        SavourTestUtil.clearAuthToken(activityRule);
    }

    @Test
    public void testRecipeCardsDisplay() throws InterruptedException {
        SavourTestUtil.login(activityRule);

        Consumer<Fragment> onInject = fragment -> ((DiscoverFragment) fragment).fetch();

        try {
            SavourTestUtil.injectViewModel(activityRule, "discoverViewModel", mDiscoverViewModel, onInject);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Couldn't inject ViewModel");
        }

        Thread.sleep(500); // Buffer for items to show

        onView(withRecyclerView(R.id.recycler_view_discover).atPositionOnView(0, R.id.recipe_card_name))
            .check(matches(withText("Recipe 0")));
        onView(withRecyclerView(R.id.recycler_view_discover).atPositionOnView(1, R.id.recipe_card_name))
            .check(matches(withText("Recipe 1")));
    }
}
