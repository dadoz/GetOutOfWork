package com.sample.lmn.davide.getoutofwork;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.sample.lmn.davide.getoutofwork.managers.RealmPersistenceManager;
import com.sample.lmn.davide.getoutofwork.presenters.TimeScheduleRegisterPresenter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.realm.Realm;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.util.Calendar.AM;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by davide-syn on 7/5/17.
 */

@RunWith(AndroidJUnit4.class)
public class TimeSchedulPresenterEspressoTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    private TimeScheduleRegisterPresenter presenter;

    @Before
    public void init() {
        //check in to make in error setCheckIn
        Realm.init(activityTestRule.getActivity());
        presenter = new TimeScheduleRegisterPresenter(activityTestRule.getActivity(),
                new RealmPersistenceManager(Realm.getDefaultInstance()));
    }

    @Test
    public void testCheckInAm() {
        assertEquals(presenter.setCheckInAm(), false);
        //find snackbar with that text
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(activityTestRule.getActivity().getString(R.string.generic_error) + AM)))
                .check(matches(isDisplayed()));
    }
}
