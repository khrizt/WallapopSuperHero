package com.christian.wallapopsuperhero.functionalTests;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.christian.wallapopsuperhero.R;
import com.christian.wallapopsuperhero.ui.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.any;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void goToDetailFirstComic() {
        onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.comic_detail_title)).check(matches(withText(any(String.class))));
    }

    @Test
    public void goToSecondPost() {
        onView(withId(R.id.list)).perform(RecyclerViewActions.scrollToPosition(1));
        onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
    }
}