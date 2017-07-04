package com.sample.lmn.davide.getoutofwork.managers

import com.sample.lmn.davide.getoutofwork.models.TimeSchedule
import io.realm.Realm
import java.util.*

/**
 * Created by davide-syn on 7/4/17.
 */
class RealmPersistenceManager(val realm: Realm) {

    /**
     * TODO add test
     */
    fun findTodayTimeSchedule() :TimeSchedule{
        return realm.where(TimeSchedule::class.java).equalTo("data", Date()).findFirst()
    }

    /**
     * TODO add test
     */
    fun findTimeSchedule(date: Date) :TimeSchedule{
        return realm.where(TimeSchedule::class.java).equalTo("data", date).findFirst()
    }

    fun saveTodayTimeSchedule() {
        TimeSchedule()
    }
}
