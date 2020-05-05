package com.sample.lmn.davide.getoutofwork.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

enum class CheckTypeEnum {
    START, END, LAUNCH_START, LAUNCH_END
}

@Entity(tableName = "goow_time_schedule")
open class TimeSchedule(
        val userId: String?,
        val checkType: Int = CheckTypeEnum.START.ordinal,
        val dayTime: Int = Calendar.AM,
        val checkTime: Date
)
{
    @PrimaryKey
    var id: String = "-1"

    fun getCheck(): CheckTypeEnum = CheckTypeEnum.values()[checkType]
}
