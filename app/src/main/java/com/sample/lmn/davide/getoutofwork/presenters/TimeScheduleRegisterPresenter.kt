package com.sample.lmn.davide.getoutofwork.presenters

import com.sample.lmn.davide.getoutofwork.managers.RealmPersistenceManager
import com.sample.lmn.davide.getoutofwork.managers.RealmPersistenceManager.TimeDateEnum.AM
import com.sample.lmn.davide.getoutofwork.managers.RealmPersistenceManager.TimeDateEnum.PM
import com.sample.lmn.davide.getoutofwork.models.TimeSchedule
import com.sample.lmn.davide.getoutofwork.views.TimeScheduleRegisterView
import java.util.*

/**
 * Created by davide-syn on 7/3/17.
 */

class TimeScheduleRegisterPresenter(val view: TimeScheduleRegisterView, val persistenceManager: RealmPersistenceManager) {

    init {
        persistenceManager.initTodayTimeSchedule()
        initView()
    }

    /**
     * check in
     */
    fun setCheckInCheckOutAm() {
        if (persistenceManager.isCheckedInToday(AM)) {
            val checkInDate: Date? = persistenceManager.checkInTodayTimeSchedule(AM)
            view.setUICheckInPm(checkInDate?: Date())
        }

        //TODO check if already out ->
        val checkInDate: Date? = persistenceManager.checkOutTodayTimeSchedule(AM)
        view.setUICheckInPm(checkInDate?: Date())
    }

    /**
     * check out
     */
    fun setCheckInCheckOutPm() {
        if (persistenceManager.isCheckedInToday(PM)) {
            val checkInDatePm: Date? = persistenceManager.checkInTodayTimeSchedule(PM)
            view.setUICheckInPm(checkInDatePm?: Date())
            return
        }

        //TODO check if already out ->
        val checkInDatePm: Date? = persistenceManager.checkOutTodayTimeSchedule(PM)
        view.setUICheckOutPm(checkInDatePm?: Date())
    }

    /**
     * init view
     */
    fun initView() {
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
