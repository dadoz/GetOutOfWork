package com.sample.lmn.davide.getoutofwork.views

import java.util.*

/**
 * Created by davide-syn on 7/3/17.
 */
interface TimeScheduleRegisterView {
    fun setUICheckInAm(date: Date)
    fun setUICheckOutAm(date: Date)
    fun setUICheckInPm(date: Date)
    fun setUICheckOutPm(date: Date)
    fun showErrorUI(dateTime: Int)

}