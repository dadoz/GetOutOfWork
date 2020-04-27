package com.sample.lmn.davide.getoutofwork.data.local

import androidx.room.*
import com.sample.lmn.davide.getoutofwork.models.TimeSchedule

@Dao
interface TimeScheduleDao {
    @Insert
    fun insertAllTimeSchedule(timeSchedule: List<TimeSchedule>)

    @Insert
    fun insertTimeSchedule(timeSchedule: TimeSchedule)

    @Update
    fun updateTimeSchedule(timeSchedule: TimeSchedule)

    @Delete
    fun deleteTimeSchedule(timeSchedule: TimeSchedule)

    @Query("SELECT * FROM goow_time_schedule")
    fun getTimeSchedules(): List<TimeSchedule>

    @Query("SELECT * FROM goow_time_schedule WHERE id=:id")
    fun findTimeScheduleById(id: String): TimeSchedule
}