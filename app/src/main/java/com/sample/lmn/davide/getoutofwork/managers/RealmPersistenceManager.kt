package com.sample.lmn.davide.getoutofwork.managers

import android.content.Context
import com.sample.lmn.davide.getoutofwork.models.OutInEnum
import com.sample.lmn.davide.getoutofwork.models.TimeSchedule
import com.sample.lmn.davide.getoutofwork.presenters.diffHours
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

//    /**
//     * TODO add test
//     */
//    fun findTimeSchedule(date: Date): TimeSchedule = realm.where(TimeSchedule::class.java)
//            .equalTo("date", date).findFirst()

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
    fun checkToday(): TimeSchedule {
        with(getTodayTimeSchedule(), {
            realm.executeTransaction {
                //set check time
                val currentDate = Date()
                this.currentCheckedDate = currentDate
                when (dateTime) {
                    AM -> when (getCheck()) {
                        OutInEnum.IN -> {
                            checkInDateAm = currentDate
                            check = OutInEnum.OUT.name
                        }
                        OutInEnum.OUT -> {
                            checkOutDateAm = currentDate
                            dateTime = PM
                            check = OutInEnum.IN.name
                        }
                    }
                    PM -> when (getCheck()) {
                        OutInEnum.IN -> {
                            checkInDatePm = currentDate
                            check = OutInEnum.OUT.name
                        }
                        OutInEnum.OUT -> {
//                            checkOutDatePM = currentDate
//                            dateTime = PM
//                            check = OutInEnum.IN.name
                        }
                    }
                }
            }
            return this
        })
    }

    /**
     * TODO add a test
     */
    fun isCheckedInToday(dateTime: Int): Boolean {
        return isCheckedToday(dateTime, OutInEnum.IN)
    }

    /**
     * TODO add a test
     */
    fun isCheckedOutToday(dateTime: Int): Boolean {
        return isCheckedToday(dateTime, OutInEnum.OUT)
    }

    /**
     * is check today
     */
    fun isCheckedToday(dateTime: Int, check: OutInEnum): Boolean {
        with(getTodayTimeSchedule(), {
            if ((dateTime == AM) and (check == OutInEnum.IN))
                return checkInDateAm != null
            if ((dateTime == AM) and (check == OutInEnum.OUT))
                return checkOutDateAm != null
            if ((dateTime == PM) and (check == OutInEnum.IN))
                return checkOutDatePm != null
            if ((dateTime == PM) and (check == OutInEnum.OUT))
                return checkOutDatePm != null
            return false
        })
    }
    /**
     * TODO add a test
     */
    fun calculateClockOutDate(): Date {
        return with(getTodayTimeSchedule(), {
            if (checkOutDatePm != null)
                checkOutDatePm as Date
            else if (checkInDatePm != null) {
                checkInDatePm as Date + (8.hours) - if (checkInDateAm == null) 0.hour else (checkInDateAm as Date).diffHours(checkOutDateAm)
            } else if (checkOutDateAm != null) {
                checkOutDateAm as Date + 8.hours + 1.hour - if (checkInDateAm == null) 0.hour else (checkInDateAm as Date).diffHours(checkOutDateAm)
            } else if (checkInDateAm != null)
                checkInDateAm as Date + 8.hours + 1.hour //take launch time
            else
                Date()
        })
    }


    /**
     *
     */
//    fun validSetCheckDate(): Boolean {
//        with (getTodayTimeSchedule()) {
//            return when (dateTime) {
//                Calendar.AM -> when (getCheck()) {
//                    OutInEnum.IN-> !isCheckedInToday(Calendar.AM) and Date().isAm()
//                    OutInEnum.OUT -> isCheckedAm() and Date().isAm()
//                }
//                Calendar.PM -> when (getCheck()) {
//                    OutInEnum.IN -> isCheckedPm() and Date().isPm()
//                    OutInEnum.OUT -> isCheckedPm2() and Date().isPm()
//                }
//                else -> {
//                    return false
//                }
//            }
//        }
//    }

    /**
     *
     */
    private fun isCheckedAm(): Boolean {
        return isCheckedInToday(Calendar.AM) and !isCheckedOutToday(Calendar.AM)
    }

    private fun isCheckedPm(): Boolean {
        return !isCheckedInToday(Calendar.PM) or isCheckedOutToday(Calendar.AM)
    }
    private fun isCheckedPm2(): Boolean {
        return isCheckedInToday(Calendar.PM) and !isCheckedOutToday(Calendar.PM)
    }
}

