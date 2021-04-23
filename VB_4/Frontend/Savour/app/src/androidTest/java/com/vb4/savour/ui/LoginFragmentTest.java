package com.vb4.savour.ui;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.vb4.savour.R;
import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.model.CreateUserResponse;
import com.vb4.savour.data.model.LoginRequest;
import com.vb4.savour.data.model.User;
import com.vb4.savour.ui.login.LoginViewModel;
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
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginFragmentTest {
    @Mock
    private LoginViewModel mMockedLoginViewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);

        SavourTestUtil.clearAuthToken(activityRule);
    }

    @After
    public void after() {
        SavourTestUtil.clearAuthToken(activityRule);
    }

    @Test
    public void testLoginSucceeds() {
        User success = new User();
        success.userId = 3;

        when(mMockedLoginViewModel.login(anyString(), anyString()))
                .thenReturn(new MutableLiveData<>(AsyncData.success(success)));

        try {
            SavourTestUtil.injectViewModel(activityRule, "mViewModel", mMockedLoginViewModel, null);
        } catch (Exception e) {
            fail();
        }

        onView(withId(R.id.edit_text_login_username))
                .perform(typeText("a"), closeSoftKeyboard());
        onView(withId(R.id.edit_text_login_password))
                .perform(typeText("a"), closeSoftKeyboard());
        onView(withId(R.id.button_login_submit)).perform(click());

        onView(withId(R.id.button_login_submit)).check(doesNotExist());
    }

    @Test
    public void testLoginFails() {
        String error = "Invalid credentials";

        when(mMockedLoginViewModel.login(anyString(), anyString()))
                .thenReturn(new MutableLiveData<>(AsyncData.error(error)));

        try {
            SavourTestUtil.injectViewModel(activityRule, "mViewModel", mMockedLoginViewModel, null);
        } catch (Exception e) {
            fail();
        }

        onView(withId(R.id.edit_text_login_username))
                .perform(typeText("a"), closeSoftKeyboard());
        onView(withId(R.id.edit_text_login_password))
                .perform(typeText("a"), closeSoftKeyboard());
        onView(withId(R.id.button_login_submit)).perform(click());

        onView(withId(R.id.recycler_view_discover)).check(doesNotExist());
    }
}
