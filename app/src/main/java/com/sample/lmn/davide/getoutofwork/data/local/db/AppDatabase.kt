package com.sample.lmn.davide.getoutofwork.data.local.db

import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.TypeConverters
import com.sample.lmn.davide.getoutofwork.data.local.TimeScheduleDao
import com.sample.lmn.davide.getoutofwork.models.TimeSchedule


@Database(entities = [TimeSchedule::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val timeScheduleDao: TimeScheduleDao
}
