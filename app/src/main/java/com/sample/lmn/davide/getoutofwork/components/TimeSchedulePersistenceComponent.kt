package com.sample.lmn.davide.getoutofwork.components

import com.sample.lmn.davide.getoutofwork.MainActivity
import com.sample.lmn.davide.getoutofwork.modules.RealmPersistenceModule
import dagger.Component

/**
 * Created by davide-syn on 7/3/17.
 */
@Component(modules = arrayOf(RealmPersistenceModule::class))
interface TimeSchedulePersistenceComponent {
    fun inject(activity: MainActivity)
}