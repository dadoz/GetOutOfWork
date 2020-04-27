package com.sample.lmn.davide.getoutofwork.presenters

import com.sample.lmn.davide.getoutofwork.managers.RealmPersistenceManager
import com.sample.lmn.davide.getoutofwork.models.OutInEnum
import com.sample.lmn.davide.getoutofwork.models.TimeScheduleRealm
import com.sample.lmn.davide.getoutofwork.ui.views.TimeScheduleRegisterView
import khronos.Duration
import khronos.toString
import khronos.with
import java.util.*
import kotlin.properties.Delegates

/**
 * Date extensions
 */
fun Date.isAm(): Boolean = this.before(this.with(hour = 12))
fun Date.isPm(): Boolean = this.equals(this.with(hour = 12)) || this.after(this.with(hour = 12))
fun Date.italianFormat(): String = this.toString("HH:mm dd MMM")
fun Date.timeFormat(): String = this.toString("HH:mm")
fun Date.diffHours(date: Date?): Duration {
    val diff : Int = if (date != null) ((date.time - this.time) / (1000 * 60 * 60)).toInt() else 0
    return Duration(Calendar.HOUR_OF_DAY, diff + 1)
}

/**
 * Created by davide-syn on 7/3/17.
 */
class TimeScheduleRegisterPresenter(private val view: TimeScheduleRegisterView,
                                    private val persistenceManager: RealmPersistenceManager) {
    init {
        persistenceManager.initTodayTimeScheduleRealm()
    }

    /**
     *
     */
    fun setCheck() {
        timeSchedule = persistenceManager.checkToday()?: return showError(timeSchedule.getCheck(), timeSchedule.dateTime)
        view.updateCheckCardview(timeSchedule)
    }

    /**
     * cehck in am
     */
    private fun showError(check: OutInEnum, dateTime: Int)  = view.showErrorUI(if (dateTime == Calendar.AM) "AM" else "PM" + " - ${check.name}")

    var timeSchedule: TimeScheduleRealm by Delegates.observable(initialValue = persistenceManager.getTodayTimeScheduleRealm(), onChange = {
        property, oldValue, newValue -> onUpdateTimeSchedule(newValue)
    })
    /**
     * init view
     */
    fun initView() {
        timeSchedule = persistenceManager.getTodayTimeScheduleRealm()
    }

    /**
     * on update tiem schedule
     */
    fun onUpdateTimeSchedule(newValue: TimeScheduleRealm) {
        //set clock time
        view.setClockOutTime(getClockOutDate())
        //update cardview
        view.updateCheckCardview(timeSchedule)
    }

    fun getClockOutDate(): Date? = persistenceManager.calculateClockOutDate()

    fun getClockToday(): TimeScheduleRealm = persistenceManager.getTodayTimeScheduleRealm()
}

