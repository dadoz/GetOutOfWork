package com.sample.lmn.davide.getoutofwork

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import com.sample.lmn.davide.getoutofwork.components.TimeSchedulePersistenceComponent
import com.sample.lmn.davide.getoutofwork.managers.RealmPersistenceManager
import com.sample.lmn.davide.getoutofwork.modules.RealmPersistenceModule
import com.sample.lmn.davide.getoutofwork.services.RealTimeBackgroundService
import com.sample.lmn.davide.getoutofwork.views.TimeScheduleRegisterView
import javax.inject.Inject

class MainActivity : AppCompatActivity(), TimeScheduleRegisterView {
    val connection: LocalServiceConnection = LocalServiceConnection()

    @Inject
    lateinit var timeSchedulePersistenceManager: RealmPersistenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //inject component dagger
        val component :TimeSchedulePersistenceComponent = DaggerTimeSchedulePersistenceComponent
                .builder()
                .realmPersistenceModule(RealmPersistenceModule(applicationContext))
                .build()
        component.inject(this)

        //bind service
        bindService(Intent(this, RealTimeBackgroundService::class.java), connection,
                Context.BIND_AUTO_CREATE)

        onInitView()
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
    }

    private fun onInitView() {
        timeSchedulePersistenceManager.findTodayTimeSchedule()
    }

    /**
     *
     */
    class LocalServiceConnection: ServiceConnection {
        lateinit var localService: RealTimeBackgroundService

        override fun onServiceConnected(componentName: ComponentName, service: IBinder) {
            val binder :RealTimeBackgroundService.LocalBinder = service as RealTimeBackgroundService.LocalBinder
            localService = binder.getService()
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
        }
    }

}
