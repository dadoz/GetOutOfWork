package com.sample.lmn.davide.getoutofwork.managers

import android.content.Context
import com.sample.lmn.davide.getoutofwork.models.TimeSchedule
import io.realm.Realm
import khronos.*
import java.util.*
import java.util.Calendar.AM
import java.util.Calendar.PM

/**
 * Created by davide-syn on 7/4/17.
 */
class RealmPersistenceManager(val applicationContext: Context) {
    val realm: Realm by lazy {
        Realm.init(applicationContext)
        Realm.getDefaultInstance()
    }

    /**
     * companion obj
     */
    object Holder {
        lateinit var instance: RealmPersistenceManager

        operator fun invoke(context: Context): Holder {
            instance = RealmPersistenceManager(applicationContext = context)
            return this
        }
    }


    /**
     * TODO add test
     */
    fun initTodayTimeSchedule(): TimeSchedule  = getTodayTimeSchedule()

    /**
     * TODO add test
     */
    fun findTimeSchedule(date: Date): TimeSchedule = realm.where(TimeSchedule::class.java)
            .equalTo("date", date).findFirst()

    /**
     * TODO add a test
     */
    fun createTodayTimeSchedule(): TimeSchedule {
        realm.executeTransaction { realm ->
            with(realm.createObject(TimeSchedule::class.java), { date = Date() }) }
        return getTodayTimeSchedule()
    }

    /**
     * TODO create a test
     */
    fun getTodayTimeSchedule(): TimeSchedule {
        return realm.where(TimeSchedule::class.java)
                .between("date", Dates.today.beginningOfDay, Dates.today.endOfDay)
                .findFirst()?: createTodayTimeSchedule()
    }

    /**
     * TODO make test
     *
     */
    fun checkInTodayTimeSchedule(dateTime :Int): Date? {
        try {
            val timeSchedule :TimeSchedule? = getTodayTimeSchedule()
            val checkDate = Date()
            realm.executeTransaction {
                if (dateTime == AM)
                    timeSchedule?.checkInDateAm = checkDate
                if (dateTime == PM)
                    timeSchedule?.checkInDatePm = checkDate
            }

            return checkDate
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    /**
     * TODO make test
     */
    fun checkOutTodayTimeSchedule(dateTime: Int): Date? {
        try {
            val timeSchedule :TimeSchedule? = getTodayTimeSchedule()
            val checkDate = Date()
            realm.executeTransaction {
                if (dateTime == AM)
                    timeSchedule?.checkOutDateAm = Date()
                if (dateTime == PM)
                    timeSchedule?.checkOutDatePm = Date()
            }
            return checkDate
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    /**
     * TODO add a test
     */
    fun isCheckedInToday(dateTime: Int): Boolean {
        val timeSchedule = getTodayTimeSchedule()

        if (dateTime == AM)
            return timeSchedule.checkInDateAm != null

        if (dateTime == PM)
            return timeSchedule.checkInDatePm != null
        return false
    }
    /**
     * TODO add a test
     */
    fun isCheckedOutToday(dateTime: Int): Boolean {
        val timeSchedule = getTodayTimeSchedule()

        if (dateTime == AM)
            return timeSchedule.checkOutDateAm != null

        if (dateTime == PM)
            return timeSchedule.checkOutDatePm != null
        return false
    }

    /**
     * TODO add a test
     */
    fun calculateClockOutDate(): Date {
        return with(getTodayTimeSchedule(), {
            if (checkOutDatePm != null)
                checkOutDatePm as Date
            else if (checkInDatePm != null)
                checkInDatePm as Date + 4.hours
            else if (checkOutDateAm != null)
                checkInDateAm as Date + 4.hours
            else if (checkInDateAm != null)
                checkInDateAm as Date + 8.hours + 1.hour //take launch time
            else
                Date()
        })
    }
}
