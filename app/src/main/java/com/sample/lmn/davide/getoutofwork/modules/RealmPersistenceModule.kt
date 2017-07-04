package com.sample.lmn.davide.getoutofwork.modules

import com.sample.lmn.davide.getoutofwork.managers.RealmPersistenceManager
import dagger.Module
import dagger.Provides
import io.realm.Realm
import javax.inject.Singleton

/**
 * Created by davide-syn on 7/3/17.
 */
@Singleton
@Module
class RealmPersistenceModule {
    val realm : Realm = Realm.getDefaultInstance()

    @Provides
    fun provideRealmPersistenceManager() : RealmPersistenceManager {
        return RealmPersistenceManager(realm)
    }
}
