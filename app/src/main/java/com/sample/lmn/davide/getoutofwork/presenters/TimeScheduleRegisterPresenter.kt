package com.sample.lmn.davide.getoutofwork.presenters

import com.sample.lmn.davide.getoutofwork.managers.RealmPersistenceManager
import com.sample.lmn.davide.getoutofwork.models.TimeSchedule
import com.sample.lmn.davide.getoutofwork.views.TimeScheduleRegisterView
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
 * in and out enum
 */
enum class OutInEnum {
    OUT, IN
}

/**
 * Created by davide-syn on 7/3/17.
 */
class TimeScheduleRegisterPresenter(val view: TimeScheduleRegisterView,
                                    val persistenceManager: RealmPersistenceManager) {
    //TODO config responsabilityChain
    private var check: OutInEnum = OutInEnum.IN
    private var dateTime: Int = Calendar.AM

    init {
        persistenceManager.initTodayTimeSchedule()
    }

    /**
     * cehck in am
     */
    fun setCheck() {
        if (!validSetCheckDate(check, dateTime)) {
            return showError(check, dateTime)
        }

        val date: Date = setCheckDate(check, dateTime)?: return showError(check, dateTime)
        view.updateCheckCardview(date, check, dateTime)
    }

    /**
     * cehck in am
     */
    fun validSetCheckDate(check: OutInEnum, dateTime: Int): Boolean {
        return when (dateTime) {
            Calendar.AM -> when (check) {
                OutInEnum.IN -> !persistenceManager.isCheckedInToday(Calendar.AM) and Date().isAm()
                OutInEnum.OUT -> persistenceManager.isCheckedInToday(Calendar.AM) and
                        !persistenceManager.isCheckedOutToday(Calendar.AM) and Date().isAm()
            }
            Calendar.PM -> when (check) {
                OutInEnum.IN -> !persistenceManager.isCheckedInToday(Calendar.PM) or
                        persistenceManager.isCheckedOutToday(Calendar.AM) and Date().isPm()
                OutInEnum.OUT -> persistenceManager.isCheckedInToday(Calendar.PM) and
                        !persistenceManager.isCheckedOutToday(Calendar.PM) and Date().isPm()
            }
            else -> {
                return false
            }
        }
    }

    /**
     *
     */
    fun setCheckDate(check: OutInEnum, dateTime: Int): Date? {
        //trigger observable
        timeSchedule = when (check) {
            OutInEnum.OUT -> persistenceManager.checkOutTodayTimeSchedule(dateTime)
            OutInEnum.IN -> persistenceManager.checkInTodayTimeSchedule(dateTime)
        }

        return timeSchedule.checkInDateAm
    }
    /**
     * cehck in am
     */
    private fun showError(check: OutInEnum, dateTime: Int)  = view.showErrorUI(if (dateTime == Calendar.AM) "AM" else "PM" + " - ${check.name}")

    var timeSchedule: TimeSchedule by Delegates.observable(initialValue = persistenceManager.getTodayTimeSchedule(), onChange = {
        property, oldValue, newValue -> onUpdateTimeSchedule(newValue)
    })
    /**
     * init view
     */
    fun initView() {
        timeSchedule = persistenceManager.getTodayTimeSchedule()
    }

    /**
     * on update tiem schedule
     */
    fun onUpdateTimeSchedule(newValue: TimeSchedule) {
        //set clock time
        view.setClockOutTime(getClockOutDate())
        if (timeSchedule.checkInDateAm != null)
            view.updateCheckCardview(timeSchedule.checkInDateAm as Date, check, dateTime)
        //update status
        with(newValue, {
            if (checkInDateAm != null) {
                check = OutInEnum.OUT
                dateTime = Calendar.AM
            }
            if (checkOutDateAm != null) {
                check = OutInEnum.IN
                dateTime = Calendar.PM
            }
            if (checkInDatePm != null) {
                check = OutInEnum.OUT
                dateTime = Calendar.PM
            }
        })

    }

    fun getClockOutDate(): Date = persistenceManager.calculateClockOutDate()

    fun getClockToday(): TimeSchedule = persistenceManager.getTodayTimeSchedule()

    fun setCheckAndDateTime(out: OutInEnum, am: Int) {
        check = out
        dateTime = am
    }
}

