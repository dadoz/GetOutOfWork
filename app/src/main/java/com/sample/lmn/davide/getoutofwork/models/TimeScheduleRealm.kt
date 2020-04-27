package com.sample.lmn.davide.getoutofwork.models

import io.realm.RealmObject
import java.util.*

/**
 * Created by davide-syn on 7/3/17.
 */

enum class OutInEnum {
    OUT, IN
}

open class TimeScheduleRealm : RealmObject() {
    var check: String = OutInEnum.IN.name
    var dateTime: Int = Calendar.AM //TODO rm it not used anymore
    var id: Long = -1
    var date: Date? = null
    var checkInDateAm: Date? = null
    var checkOutDateAm: Date? = null
    var checkInDatePm: Date? = null
    var checkOutDatePm: Date? = null
    var currentCheckedDate: Date? = null

    fun getCheck(): OutInEnum {
        return OutInEnum.valueOf(check)
    }
}
