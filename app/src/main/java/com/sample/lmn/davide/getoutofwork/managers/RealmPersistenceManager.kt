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

    /**
     * TODO add test
     */
    fun initTodayTimeSchedule() {
        if (getTodayTimeSchedule()== null)
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
}
