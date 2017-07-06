package com.sample.lmn.davide.getoutofwork.managers

import com.sample.lmn.davide.getoutofwork.models.TimeSchedule
import io.realm.Realm
import khronos.Dates
import khronos.beginningOfDay
import java.util.*
import javax.inject.Singleton

/**
 * Created by davide-syn on 7/4/17.
 */
@Singleton
class RealmPersistenceManager(val realm: Realm) {
    enum class DateTimeEnum {
        AM, PM,
    }
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
    fun checkInTodayTimeSchedule(dateTime :DateTimeEnum): Date? {
        try {
            val timeSchedule :TimeSchedule? = getTodayTimeSchedule()
            val checkDate = Date()
            realm.executeTransaction {
                if (dateTime == DateTimeEnum.AM)
                    timeSchedule?.checkInDateAm = checkDate
                if (dateTime == DateTimeEnum.PM)
                    timeSchedule?.checkInDateAm = checkDate
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
    fun checkOutTodayTimeSchedule(dateTime :DateTimeEnum): Date? {
        try {
            val timeSchedule :TimeSchedule? = getTodayTimeSchedule()
            val checkDate = Date()
            realm.executeTransaction {
                if (dateTime == DateTimeEnum.AM)
                    timeSchedule?.checkOutDateAm = Date()
                if (dateTime == DateTimeEnum.PM)
                    timeSchedule?.checkOutDateAm = Date()
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
    fun isCheckedInToday(dateTime: DateTimeEnum): Boolean {
        val timeSchedule = getTodayTimeSchedule()

        if (dateTime == DateTimeEnum.AM)
            return timeSchedule?.checkInDateAm != null

        if (dateTime == DateTimeEnum.PM)
            return timeSchedule?.checkInDatePm != null
        return false
    }
    /**
     * TODO add a test
     */
    fun isCheckedOutToday(dateTime: DateTimeEnum): Boolean {
        val timeSchedule = getTodayTimeSchedule()

        if (dateTime == DateTimeEnum.AM)
            return timeSchedule?.checkOutDateAm != null

        if (dateTime == DateTimeEnum.PM)
            return timeSchedule?.checkOutDatePm != null
        return false
    }
}
