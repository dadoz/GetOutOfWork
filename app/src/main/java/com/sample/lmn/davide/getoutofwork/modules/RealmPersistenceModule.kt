package com.sample.lmn.davide.getoutofwork.modules

import com.sample.lmn.davide.getoutofwork.models.TimeSchedule
import com.vicpin.krealmextensions.queryFirst
import dagger.Module
import dagger.Provides
import io.realm.Realm
import java.util.*
import javax.inject.Singleton

/**
 * Created by davide-syn on 7/3/17.
 */
@Singleton
@Module
class RealmPersistenceModule {
    val realm = Realm.getDefaultInstance()

    @Provides
    fun provideTodayTimeSchedule() : TimeSchedule? {
        return TimeSchedule().queryFirst { query -> query.equalTo("data", Date())}
    }
}