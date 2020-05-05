package com.sample.lmn.davide.getoutofwork.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.room.Room
import com.sample.lmn.davide.getoutofwork.data.local.db.AppDatabase
import com.sample.lmn.davide.getoutofwork.data.remote.TimeScheduleService
import com.sample.lmn.davide.getoutofwork.models.TimeSchedule
import java.lang.Exception

class TimeScheduleRepository(context: Context) {
    private val database : AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "db-timeschedule")
            .allowMainThreadQueries()   //Allows room to do operation on main thread
            .build()
    private val timeScheduleService = TimeScheduleService()

    private val local = false
    fun getTimeSchedule() : LiveData<List<TimeSchedule>> {
        if (local) {
            return liveData {
                try {
                    emit(database.timeScheduleDao.getTimeSchedules())
                } catch (exception: Exception) {
                    Result.failure<String>(exception)
                }
            }
        }
        return timeScheduleService.getTimeSchedule()
    }

    fun addTimeSchedule(timeSchedule: TimeSchedule) = liveData {
        if (local) {
            try {
                //sequentially emit coroutines
                emit(database.timeScheduleDao.insertTimeSchedule(timeSchedule))
            } catch (exception: Exception) {
                Result.failure<String>(exception)
                exception.printStackTrace()
            }
        } else {
            timeScheduleService.addTimeSchedule(timeSchedule)
        }

    }
}