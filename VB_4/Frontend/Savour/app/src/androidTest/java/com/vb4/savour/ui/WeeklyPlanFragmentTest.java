package com.vb4.savour.ui;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.core.util.Consumer;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.vb4.savour.R;
import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.model.Recipe;
import com.vb4.savour.data.model.WeeklyPlanResponsePiece;
import com.vb4.savour.ui.main.MainActivity;
import com.vb4.savour.ui.weeklyplan.WeeklyPlanFragment;
import com.vb4.savour.ui.weeklyplan.WeeklyPlanViewModel;

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
public class WeeklyPlanFragmentTest {
    @Mock
    public WeeklyPlanViewModel mWeeklyPlanViewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    private final LiveData<AsyncData<WeeklyPlanResponsePiece[]>> fakeSuccess;

    public WeeklyPlanFragmentTest() {
        WeeklyPlanResponsePiece[] pieces = {new WeeklyPlanResponsePiece()};

        Recipe[] recipes = SavourTestUtil.fakeRecipes();
        String day = "SUN";

            pieces[0].day = day;
            pieces[0].recipes = new Recipe[3];
            pieces[0].recipes[0] = recipes[0];
            pieces[0].recipes[1] = recipes[0];
            pieces[0].recipes[2] = recipes[0];


        fakeSuccess = new MutableLiveData<>(AsyncData.success(pieces));
    }

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Setup mock weekly plan
        when(mWeeklyPlanViewModel.getWeeklyPlan())
                .thenReturn(fakeSuccess);
    }

    @After
    public void teardown() {
        SavourTestUtil.clearAuthToken(activityRule);
    }

    @Test
    public void testHeaderDisplays() throws InterruptedException {
        SavourTestUtil.login(activityRule);

        Consumer<Fragment> onInject = fragment -> ((WeeklyPlanFragment)fragment).fetch();

        onView(withId(R.id.navigation_weekly_plan)).perform(click());

        try {
            SavourTestUtil.injectViewModel(activityRule, "weeklyPlanViewModel", mWeeklyPlanViewModel, onInject);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Couldn't inject ViewModel");
        }

        Thread.sleep(500); // Buffer for items to show

        onView(withRecyclerView(R.id.recycler_view_weekly_plan).atPositionOnView(0, R.id.weekday_name))
                .check(matches(withText("SUN")));
    }
}

