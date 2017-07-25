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

enum class OutInEnum {
    OUT, IN
}

/**
 * Created by davide-syn on 7/3/17.
 */
class TimeScheduleRegisterPresenter(val view: TimeScheduleRegisterView,
                                    val persistenceManager: RealmPersistenceManager) {
    //TODO config responsabilityChain
    var out: OutInEnum = OutInEnum.IN
    var dateTime: Int = Calendar.AM

    init {
        persistenceManager.initTodayTimeSchedule()
    }

    /**
     * cehck in am
     */
    fun setCheck(): Boolean {
        val date = setCheckDate(out, dateTime)
        if (date != null) {
            view.updateCheckCardview(date, out, dateTime)
            return true
        }

        showError(dateTime)
        return false
    }

    /**
     * cehck in am
     */
    fun setCheckDate(check: OutInEnum, dateTime: Int): Date? {
        if (check == OutInEnum.OUT)
            return if (!persistenceManager.isCheckedInToday(dateTime) or
                    persistenceManager.isCheckedOutToday(dateTime)) null else
                persistenceManager.checkOutTodayTimeSchedule(dateTime)
        else
            return if (persistenceManager.isCheckedInToday(dateTime)) null else
                persistenceManager.checkInTodayTimeSchedule(dateTime)

    }

    /**
     * cehck in am
     */
    private fun showError(dateTime: Int)  = view.showErrorUI(dateTime)

    /**
     * init view
     */
    fun initView() {
        val timeSchedule: TimeSchedule by Delegates
                .observable(initialValue = persistenceManager.getTodayTimeSchedule(),
                        onChange = {property, oldValue, newValue ->

                            //set clock time
                            view.setClockOutTime(getClockOutDate())

                            //update status
                            with(newValue, {
                                if (checkInDateAm != null) {
                                    out = OutInEnum.OUT
                                    dateTime = Calendar.AM
                                }
                                if (checkOutDateAm != null) {
                                    out = OutInEnum.IN
                                    dateTime = Calendar.PM
                                }
                                if (checkInDatePm != null) {
                                    out = OutInEnum.OUT
                                    dateTime = Calendar.PM
                                }
                            })
                        })
    }

    fun getClockOutDate(): Date = persistenceManager.calculateClockOutDate()

    fun getClockToday(): TimeSchedule = persistenceManager.getTodayTimeSchedule()
}

