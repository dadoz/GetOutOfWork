package com.sample.lmn.davide.getoutofwork.managers

import android.content.Context
import com.sample.lmn.davide.getoutofwork.models.OutInEnum
import com.sample.lmn.davide.getoutofwork.models.TimeScheduleRealm
import com.sample.lmn.davide.getoutofwork.presenters.diffHours
import com.sample.lmn.davide.getoutofwork.presenters.isPm
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
//        val test = RealmConfiguration.Builder(applicationContext).deleteRealmIfMigrationNeeded().build()
//        Realm.setDefaultConfiguration(test)
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
    fun initTodayTimeScheduleRealm(): TimeScheduleRealm  = getTodayTimeScheduleRealm()

    /**
     * TODO add a test
     */
    fun createTodayTimeScheduleRealm(): TimeScheduleRealm {
        realm.executeTransaction {
            realm -> with(realm.createObject(TimeScheduleRealm::class.java), { date = Date() })
        }
        return getTodayTimeScheduleRealm()
    }

    /**
     * TODO create a test
     */
    fun getTodayTimeScheduleRealm(): TimeScheduleRealm {
        //set time schedule
        val timeSchedule = realm.where(TimeScheduleRealm::class.java)
                .between("date", Dates.today.beginningOfDay, Dates.today.endOfDay)
                .findFirst()?: createTodayTimeScheduleRealm()
        return timeSchedule.apply { if ((dateTime == AM) and (check == OutInEnum.IN.name) and (Date().isPm())) realm.executeTransaction { dateTime = PM }}
    }

    /**
     * TODO make test
     *
     */
    fun checkToday(): TimeScheduleRealm? {
        val currentDate = Date()
        with(getTodayTimeScheduleRealm()) {
            return when(dateTime) {
                AM -> when (getCheck()) {
                    OutInEnum.IN -> {
                        executeTransactionConditionally(!isCheckedInAm(), Realm.Transaction {
                            currentCheckedDate = currentDate
                            checkInDateAm = currentDate
                            check = OutInEnum.OUT.name
                        }, this)
                    }
                    OutInEnum.OUT -> {
                        executeTransactionConditionally(!isCheckedOutAm(), Realm.Transaction {
                            currentCheckedDate = currentDate
                            checkOutDateAm = currentDate
                            dateTime = PM
                            check = OutInEnum.IN.name
                        }, this)
                    }
                }
                PM -> when (getCheck()) {
                    OutInEnum.IN -> {
                        executeTransactionConditionally(!isCheckedInPm(), Realm.Transaction {
                            currentCheckedDate = currentDate
                            checkInDatePm = currentDate
                            check = OutInEnum.OUT.name
                        }, this)
                    }
                    OutInEnum.OUT -> {
                        executeTransactionConditionally(!isCheckedInPm(), Realm.Transaction {
                            currentCheckedDate = currentDate
//                            checkOutDatePm = currentDate //TODO test mode if you get out before you dont check!
                            check = OutInEnum.OUT.name
                        }, this)
                        //to avoid check
                        null
                    }
                }
                else -> {
                    null
                }
            }
        }
    }

    /**
     * is check today
     */
    fun isCheckAt(dateTime: Int, check: OutInEnum): Boolean {
        with(getTodayTimeScheduleRealm(), {
            return when {
                ((dateTime == AM) and (check == OutInEnum.IN)) -> checkInDateAm != null
                ((dateTime == AM) and (check == OutInEnum.OUT)) -> checkOutDateAm != null
                ((dateTime == PM) and (check == OutInEnum.IN)) -> checkOutDatePm != null
                ((dateTime == PM) and (check == OutInEnum.OUT)) -> checkOutDatePm != null
                else -> false
            }
        })
    }
    /**
     * TODO add a test
     */
    fun calculateClockOutDate(): Date? {
        return with(getTodayTimeScheduleRealm(), {
            when {
                checkOutDatePm != null -> checkOutDatePm as Date
                checkInDatePm != null -> checkInDatePm as Date + (8.hours) - if (checkInDateAm == null) 0.hour
                else (checkInDateAm as Date).diffHours(checkOutDateAm)
                checkOutDateAm != null -> checkOutDateAm as Date + 8.hours + 1.hour - if (checkInDateAm == null) 0.hour
                else (checkInDateAm as Date).diffHours(checkOutDateAm)
                checkInDateAm != null -> checkInDateAm as Date + 8.hours + 1.hour //take launch time
                else -> null
            }
        })
    }

    /**
     * execute only on certain condition otw return false
     */
    fun executeTransactionConditionally(isChecked: Boolean, transactionAsync: Realm.Transaction, timeSchedule: TimeScheduleRealm): TimeScheduleRealm?  =
            if (isChecked) { realm.executeTransaction(transactionAsync); timeSchedule } else null

//    class RealmTransactionDelegate(val realm: Realm, val transaction: Realm.Transaction, val isChecked: Boolean) : ReadWriteProperty<Any?, TimeScheduleRealm?> {
//
//        override fun getValue(thisRef: Any?, property: KProperty<*>): TimeScheduleRealm? {
//            return if (isChecked) { realm.executeTransaction(transaction); property } else null
//        }
//
//        override fun setValue(thisRef: Any?, property: KProperty<*>, value: TimeScheduleRealm?) {
//
//        }
//    }
    /**
     *
     */
    private fun isCheckedInAm(): Boolean  = isCheckAt(Calendar.AM, OutInEnum.IN)
    /**
     *
     */
    private fun isCheckedOutAm(): Boolean = isCheckAt(Calendar.AM, OutInEnum.IN) and isCheckAt(Calendar.AM, OutInEnum.OUT)

    /**
     *
     */
    private fun isCheckedInPm(): Boolean = isCheckAt(Calendar.PM, OutInEnum.IN)

    /**
     *
     */
    private fun isCheckedOutPm(): Boolean  = isCheckAt(Calendar.PM, OutInEnum.IN) and !isCheckAt(Calendar.PM, OutInEnum.OUT)


}

