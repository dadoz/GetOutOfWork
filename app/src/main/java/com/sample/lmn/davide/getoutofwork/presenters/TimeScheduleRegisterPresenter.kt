package com.sample.lmn.davide.getoutofwork.presenters

import com.sample.lmn.davide.getoutofwork.managers.RealmPersistenceManager
import com.sample.lmn.davide.getoutofwork.managers.RealmPersistenceManager.DateTimeEnum
import com.sample.lmn.davide.getoutofwork.managers.RealmPersistenceManager.DateTimeEnum.AM
import com.sample.lmn.davide.getoutofwork.managers.RealmPersistenceManager.DateTimeEnum.PM
import com.sample.lmn.davide.getoutofwork.models.TimeSchedule
import com.sample.lmn.davide.getoutofwork.views.TimeScheduleRegisterView
import khronos.with
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
    fun setCheckInAm() :Boolean{
        if (Date().isAm()) {
            val date: Date? = setCheckInDate(AM)
            if (date != null) {
                view.setUICheckInAm(date)
                return true
            }
        }

        return setCheckOutAm()
    }

    /**
     * cehck in am
     */
    fun setCheckOutAm() :Boolean{
        if (Date().isAm()) {
            val date: Date? = setCheckOutDate(AM)
            if (date != null) {
                view.setUICheckOutAm(date)
                return true
            }
        }

        showError(AM)
        return false
    }

    /**
     * cehck in am
     */
    fun setCheckInPm() :Boolean{
        if (Date().isPm()) {
            val date: Date? = setCheckInDate(PM)
            if (date != null) {
                view.setUICheckInPm(date)
                return true
            }
        }
        return setCheckOutPm()
    }

    /**
     * cehck in am
     */
    fun setCheckOutPm(): Boolean {
        if (Date().isPm()) {
            val date: Date? = setCheckOutDate(PM)
            if (date != null) {
                view.setUICheckOutPm(date)
                return true
            }
        }

        showError(PM)
        return false
    }

    /**
     * cehck in am
     */
    fun setCheckOutDate(dateTime: DateTimeEnum): Date? {
        return if (persistenceManager.isCheckedOutToday(dateTime)) null else
            persistenceManager.checkOutTodayTimeSchedule(dateTime)
    }

    /**
     * cehck in am
     */
    fun setCheckInDate(dateTime: DateTimeEnum): Date? {
        return if (persistenceManager.isCheckedInToday(dateTime)) null else
            persistenceManager.checkInTodayTimeSchedule(dateTime)
    }

    /**
     * cehck in am
     */
    private fun showError(dateTime: DateTimeEnum) {
        view.showErrorUI(dateTime)
    }

    /**
     * init view
     */
    fun initView() {
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

    /**
     * extension
     */
    private fun Date.isAm(): Boolean{
        return this.before(this.with(hour = 12))
    }

    private fun Date.isPm(): Boolean{
        return this.equals(this.with(hour = 12)) ||
            this.after(this.with(hour = 12))
    }

}
