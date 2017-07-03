package com.sample.lmn.davide.getoutofwork.models

import io.realm.RealmObject
import java.util.*

/**
 * Created by davide-syn on 7/3/17.
 */
open class TimeSchedule : RealmObject() {
    var id: Long = -1
    lateinit var date: Date
    lateinit var checkInDateAm: Date
    lateinit var checkOutDateAm: Date
    lateinit var checkInDatePm: Date
    lateinit var checkOutDatePm: Date
}