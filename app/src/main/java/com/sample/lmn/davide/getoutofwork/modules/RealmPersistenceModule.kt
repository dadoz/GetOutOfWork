package com.sample.lmn.davide.getoutofwork.modules

import android.content.Context
import com.sample.lmn.davide.getoutofwork.managers.RealmPersistenceManager
import dagger.Module
import dagger.Provides
import io.realm.Realm

/**
 * Created by davide-syn on 7/3/17.
 */
@Module
class RealmPersistenceModule(applicationContext: Context) {
    val realm : Realm

    init {
        Realm.init(applicationContext)
        realm = Realm.getDefaultInstance()
    }

    @Provides
    fun provideRealmPersistenceManager() : RealmPersistenceManager {
        return RealmPersistenceManager(realm)
    }
}
