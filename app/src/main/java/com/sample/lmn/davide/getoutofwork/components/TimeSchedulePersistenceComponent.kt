package com.sample.lmn.davide.getoutofwork.components

import com.sample.lmn.davide.getoutofwork.MainActivity
import com.sample.lmn.davide.getoutofwork.modules.RealmPersistenceModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by davide-syn on 7/3/17.
 */
@Singleton
@Component(modules = arrayOf(RealmPersistenceModule::class))
interface TimeSchedulePersistenceComponent {
    fun inject(activity: MainActivity)
}