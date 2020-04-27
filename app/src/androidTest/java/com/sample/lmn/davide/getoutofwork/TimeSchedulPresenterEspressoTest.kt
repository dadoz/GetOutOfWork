package com.sample.lmn.davide.getoutofwork

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.ViewInteraction
import android.support.test.runner.AndroidJUnit4
import com.sample.lmn.davide.getoutofwork.managers.RealmPersistenceManager
import com.sample.lmn.davide.getoutofwork.models.TimeScheduleRealm
import com.sample.lmn.davide.getoutofwork.presenters.TimeScheduleRegisterPresenter
import com.sample.lmn.davide.getoutofwork.ui.views.TimeScheduleRegisterView
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 * Created by davide-syn on 7/5/17.
 */

@RunWith(AndroidJUnit4::class)
class TimeSchedulPresenterEspressoTest {
    lateinit var presenter: TimeScheduleRegisterPresenter

    @Before
    fun init() {
        //check in to make in error setCheckIn
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            presenter = TimeScheduleRegisterPresenter(object: TimeScheduleRegisterView {
                override fun setClockOutTime(date: Date?) {
                }

                override fun showErrorUI(message: String) {
                }

                override fun updateCheckCardview(timeSchedule: TimeScheduleRealm) {
                }
            }, RealmPersistenceManager.Holder.instance)
        }
    }

    @Test
    fun testCheckEmptySnackbar() {
        //find snackbar with that text
//        onViewErrorSnackbar(activityTestRule.activity.getString(R.string.generic_error) + AM).check(matches(isDisplayed()))
    }

    @Test
    fun testCheckIn() {
            presenter.setCheck()
        //find snackbar with that text
//        onViewErrorSnackbar(activityTestRule.activity.getString(R.string.generic_error) + AM).check(matches(isDisplayed()))

    }
    @Test
    fun testCheckOut() {
//            presenter.setCheckAndDateTime(OutInEnum.OUT, AM)
            presenter.setCheck()
        //find snackbar with that text
//        onViewErrorSnackbar(activityTestRule.activity.getString(R.string.generic_error) + PM).check(matches(isDisplayed()))
    }

    @Test
    fun testValidSetCheckDateAM_OUT() {

    }

    @Test
    fun testValidSetCheckDateAM_IN() {

    }
    @Test
    fun testValidSetCheckDatePM_OUT() {
    }

    @Test
    fun testValidSetCheckDatePM_IN() {
    }

    private fun onViewErrorSnackbar(text: String): Unit {
//        return onView(allOf<View>(withId(androidx.R.id.snackbar_text), withText(text)))
    }

}
