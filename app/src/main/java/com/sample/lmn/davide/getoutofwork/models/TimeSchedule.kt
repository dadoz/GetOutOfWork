package com.sample.lmn.davide.getoutofwork.models

import io.realm.RealmObject
import java.util.*

/**
 * Created by davide-syn on 7/3/17.
 */

enum class OutInEnum {
    OUT, IN
}

open class TimeSchedule : RealmObject() {
    var check: String = OutInEnum.IN.toString()
    var dateTime: Int = Calendar.AM
    var id: Long = -1
    var date: Date? = null
    var checkInDateAm: Date? = null
    var checkOutDateAm: Date? = null
    var checkInDatePm: Date? = null
    var checkOutDatePm: Date? = null
    var currentCheckedDate: Date? = null

    fun setCheck(check: OutInEnum) {
        this.check = check.toString()
    }

    fun getCheck(): OutInEnum {
        return OutInEnum.valueOf(check)
    }
}
