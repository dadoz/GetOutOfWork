package com.sample.lmn.davide.getoutofwork.managers

import com.sample.lmn.davide.getoutofwork.models.TimeSchedule
import io.realm.Realm
import khronos.Dates
import java.util.*
import javax.inject.Singleton

/**
 * Created by davide-syn on 7/4/17.
 */
@Singleton
class RealmPersistenceManager(val realm: Realm) {
    enum class TimeDateEnum {
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
            return realm.where(TimeSchedule::class.java).between("date", Dates.yesterday, Dates.tomorrow).findFirst()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    /**
     * TODO make test
     *
     */
    fun checkInTodayTimeSchedule(timeDate :TimeDateEnum): Date? {
        try {
            val timeSchedule :TimeSchedule? = getTodayTimeSchedule()
            val checkDate = Date()
            realm.executeTransaction {
                if (timeDate == TimeDateEnum.AM)
                    timeSchedule?.checkInDateAm = checkDate
                if (timeDate == TimeDateEnum.PM)
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
    fun checkOutTodayTimeSchedule(timeDate :TimeDateEnum): Date? {
        try {
            val timeSchedule :TimeSchedule? = getTodayTimeSchedule()
            val checkDate = Date()
            realm.executeTransaction {
                if (timeDate == TimeDateEnum.AM)
                    timeSchedule?.checkOutDateAm = Date()
                if (timeDate == TimeDateEnum.PM)
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
    fun isCheckedInToday(timeDate: TimeDateEnum): Boolean {
        val timeSchedule = getTodayTimeSchedule()

        if (timeDate == TimeDateEnum.AM)
            return timeSchedule?.checkInDateAm != null

        if (timeDate == TimeDateEnum.PM)
            return timeSchedule?.checkInDatePm != null
        return false
    }
    /**
     * TODO add a test
     */
    fun isCheckedOutToday(timeDate: TimeDateEnum): Boolean {
        val timeSchedule = getTodayTimeSchedule()

        if (timeDate == TimeDateEnum.AM)
            return timeSchedule?.checkOutDateAm != null

        if (timeDate == TimeDateEnum.PM)
            return timeSchedule?.checkOutDatePm != null
        return false
    }
}
