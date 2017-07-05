package com.sample.lmn.davide.getoutofwork.presenters

import com.sample.lmn.davide.getoutofwork.managers.RealmPersistenceManager
import com.sample.lmn.davide.getoutofwork.managers.RealmPersistenceManager.TimeDateEnum
import com.sample.lmn.davide.getoutofwork.managers.RealmPersistenceManager.TimeDateEnum.AM
import com.sample.lmn.davide.getoutofwork.managers.RealmPersistenceManager.TimeDateEnum.PM
import com.sample.lmn.davide.getoutofwork.models.TimeSchedule
import com.sample.lmn.davide.getoutofwork.views.TimeScheduleRegisterView
import java.util.*

/**
 * Created by davide-syn on 7/3/17.
 */

class TimeScheduleRegisterPresenter(val view: TimeScheduleRegisterView, val persistenceManager: RealmPersistenceManager) {
    //TODO config responsabilityChain

    init {
        persistenceManager.initTodayTimeSchedule()
        initView()
    }

    /**
     * cehck in am
     */
    fun setCheckInAm() {
        if (setCheckInDate(AM)) {
            setCheckOutAm()
        }
    }

    /**
     * cehck in am
     */
    fun setCheckInPm() {
        if (setCheckInDate(PM)) {
            setCheckOutPm()
        }
    }

    /**
     * cehck in am
     */
    fun setCheckOutAm() {
        if (setCheckOutDate(AM)) {
            showError()
        }
    }

    /**
     * cehck in am
     */
    fun setCheckOutPm() {
        if (setCheckOutDate(PM)) {
            showError()
        }
    }

    /**
     * cehck in am
     */
    fun setCheckOutDate(timeDate: TimeDateEnum): Boolean {
        if (persistenceManager.isCheckedOutToday(timeDate)) {
            return false
        }
        val checkDate: Date? = persistenceManager.checkOutTodayTimeSchedule(timeDate)
        view.setUICheckOutPm(checkDate?: Date())
        return true
    }

    /**
     * cehck in am
     */
    fun setCheckInDate(timeDate: TimeDateEnum): Boolean {
        if (persistenceManager.isCheckedInToday(timeDate)) {
            return false
        }
        val checkInDate: Date? = persistenceManager.checkInTodayTimeSchedule(timeDate)
        view.setUICheckInPm(checkInDate?: Date())
        return true
    }

    /**
     * cehck in am
     */
    private fun showError() {
    }

    /**
     * check out
     */
    fun setCheckInCheckOutPm() {
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
