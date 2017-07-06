package com.sample.lmn.davide.getoutofwork.managers

import com.sample.lmn.davide.getoutofwork.models.TimeSchedule
import io.realm.Realm
import khronos.Dates
import khronos.beginningOfDay
import java.util.*
import java.util.Calendar.AM
import java.util.Calendar.PM
import javax.inject.Singleton

/**
 * Created by davide-syn on 7/4/17.
 */
@Singleton
class RealmPersistenceManager(val realm: Realm) {
    /**
     * TODO add test
     */
    fun initTodayTimeSchedule() {
        if (getTodayTimeSchedule() == null)
            createTodayTimeSchedule()
    }

    /**
     * TODO add test
     */
    fun findTimeSchedule(date: Date) :TimeSchedule{
        return realm.where(TimeSchedule::class.java).equalTo("date", date).findFirst()
    }

    /**
     * TODO add a test
     */
    fun createTodayTimeSchedule() {
        realm.executeTransaction {
            realm ->
                with(realm.createObject(TimeSchedule::class.java), {
                    date = Date()
                })
        }
    }

    /**
     * TODO create a test
     */
    fun getTodayTimeSchedule(): TimeSchedule? {
        try {
            return realm.where(TimeSchedule::class.java).between("date", Dates.today.beginningOfDay, Dates.tomorrow).findFirst()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
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
            return timeSchedule?.checkInDateAm != null

        if (dateTime == PM)
            return timeSchedule?.checkInDatePm != null
        return false
    }
    /**
     * TODO add a test
     */
    fun isCheckedOutToday(dateTime: Int): Boolean {
        val timeSchedule = getTodayTimeSchedule()

        if (dateTime == AM)
            return timeSchedule?.checkOutDateAm != null

        if (dateTime == PM)
            return timeSchedule?.checkOutDatePm != null
        return false
    }
}
