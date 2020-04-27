package com.sample.lmn.davide.getoutofwork.models

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

enum class CheckTypeEnum {
    START, END, LAUNCH_START, LAUNCH_END
}

@Entity(tableName = "goow_time_schedule")
open class TimeSchedule(
        val checkType: Int = CheckTypeEnum.START.ordinal,
        val dayTime: Int = Calendar.AM,
        val checkTime: Date)
{
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    fun getCheck(): CheckTypeEnum = CheckTypeEnum.values()[checkType]
}
