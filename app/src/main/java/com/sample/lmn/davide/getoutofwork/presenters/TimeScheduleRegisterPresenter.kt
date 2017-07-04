package com.sample.lmn.davide.getoutofwork.presenters

import com.sample.lmn.davide.getoutofwork.managers.RealmPersistenceManager
import com.sample.lmn.davide.getoutofwork.models.TimeSchedule
import com.sample.lmn.davide.getoutofwork.views.TimeScheduleRegisterView

/**
 * Created by davide-syn on 7/3/17.
 */

class TimeScheduleRegisterPresenter(val view: TimeScheduleRegisterView, val persistenceManager: RealmPersistenceManager) {

    init {
        persistenceManager.initTodayTimeSchedule()
        setCheckInCheckOut()
    }

    fun setCheckInCheckOut() {
        println("set check in and check out")
        val timeSchedule: TimeSchedule? = persistenceManager.getTodayTimeSchedule()

        if (timeSchedule != null)
            with(timeSchedule, {
                if (checkInDateAm != null)
                    view.setUICheckInAm(checkInDateAm!!)

                if (checkOutDateAm != null)
                    view.setUICheckOutAm(checkOutDateAm!!)

                if (checkInDatePm != null)
                    view.setUICheckInPm(checkInDatePm!!)

                if (checkInDatePm != null)
                    view.setUICheckOutPm(checkInDatePm!!)
            })
    }
}
