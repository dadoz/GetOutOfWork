package com.sample.lmn.davide.getoutofwork

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import com.sample.lmn.davide.getoutofwork.managers.RealmPersistenceManager
import com.sample.lmn.davide.getoutofwork.presenters.OutInEnum
import com.sample.lmn.davide.getoutofwork.presenters.TimeScheduleRegisterPresenter
import com.sample.lmn.davide.getoutofwork.views.TimeScheduleRegisterView
import junit.framework.Assert.assertEquals
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import java.util.Calendar.AM
import java.util.Calendar.PM

/**
 * Created by davide-syn on 7/5/17.
 */

@RunWith(AndroidJUnit4::class)
class TimeSchedulPresenterEspressoTest {
    @Rule @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java)
    lateinit var presenter: TimeScheduleRegisterPresenter

    @Before
    fun init() {
        //check in to make in error setCheckIn
        InstrumentationRegistry.getInstrumentation().runOnMainSync({
            presenter = TimeScheduleRegisterPresenter(object: TimeScheduleRegisterView {
                override fun setClockOutTime(date: Date) {
                }

                override fun showErrorUI(dateTime: Int) {
                }

                override fun updateCheckCardview(date: Date, check: OutInEnum, dateTime: Int) {
                }
            }, RealmPersistenceManager.Holder.instance)
        })
    }

    @Test
    fun testCheckEmptySnackbar() {
        //find snackbar with that text
        onView(allOf<View>(withId(android.support.design.R.id.snackbar_text), withText(activityTestRule.activity.getString(R.string.generic_error) + AM)))
                .check(matches(isDisplayed()))
    }

    @Test
    fun testCheckIn() {
        presenter.setCheck()
        //find snackbar with that text
        onView(allOf<View>(withId(android.support.design.R.id.snackbar_text), withText(activityTestRule.activity.getString(R.string.generic_error) + AM)))
                .check(matches(isDisplayed()))
    }
    @Test
    fun testCheckOut() {
        presenter.setCheckAndDateTime(OutInEnum.OUT, AM)
        presenter.setCheck()
        //find snackbar with that text
        onViewErrorSnackbar().check(matches(isDisplayed()))
    }

    @Test
    fun testValidSetCheckDateAM_OUT() {
        activityTestRule.runOnUiThread {
            assertEquals(presenter.validSetCheckDate(OutInEnum.OUT, AM), true)
        }

    }

    @Test
    fun testValidSetCheckDateAM_IN() {
        activityTestRule.runOnUiThread {
            assertEquals(presenter.validSetCheckDate(OutInEnum.IN, AM), true)
        }

    }
    @Test
    fun testValidSetCheckDatePM_OUT() {
        activityTestRule.runOnUiThread {
            assertEquals(presenter.validSetCheckDate(OutInEnum.OUT, PM), true)
        }
    }

    @Test
    fun testValidSetCheckDatePM_IN() {
        activityTestRule.runOnUiThread {
            assertEquals(presenter.validSetCheckDate(OutInEnum.IN, PM), true)
        }
    }

    private fun onViewErrorSnackbar(): ViewInteraction {
        return onView(allOf<View>(withId(android.support.design.R.id.snackbar_text), withText(activityTestRule.activity.getString(R.string.generic_error) + AM)))
    }
    
}
