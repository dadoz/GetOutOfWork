package com.sample.lmn.davide.getoutofwork

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.sample.lmn.davide.getoutofwork.components.DaggerTimeSchedulePersistenceComponent
import com.sample.lmn.davide.getoutofwork.components.TimeSchedulePersistenceComponent
import com.sample.lmn.davide.getoutofwork.managers.RealmPersistenceManager
import com.sample.lmn.davide.getoutofwork.modules.RealmPersistenceModule
import com.sample.lmn.davide.getoutofwork.presenters.TimeScheduleRegisterPresenter
import com.sample.lmn.davide.getoutofwork.services.RealTimeBackgroundService
import com.sample.lmn.davide.getoutofwork.views.TimeScheduleRegisterView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), TimeScheduleRegisterView {

    val connection: LocalServiceConnection = LocalServiceConnection()
    lateinit var presenter: TimeScheduleRegisterPresenter

    @Inject lateinit var timeSchedulePersistenceManager: RealmPersistenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //init component
        onInit()

        //init view
        onInitView()
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
    }

    /**
     * init view
     */
    private fun onInit() {
        //inject component dagger
        component = DaggerTimeSchedulePersistenceComponent
                .builder()
                .realmPersistenceModule(RealmPersistenceModule(applicationContext))
                .build()
        component.inject(this)

        //bind service
        bindService(Intent(this, RealTimeBackgroundService::class.java), connection,
                Context.BIND_AUTO_CREATE)

        presenter = TimeScheduleRegisterPresenter(this, timeSchedulePersistenceManager)
    }

    private fun onInitView() {
        checkInCardViewId.setOnClickListener {
            presenter.setCheckInAm()
        }
        checkOutCardViewId.setOnClickListener {
            presenter.setCheckInPm()
        }
    }

    override fun setUICheckInAm(date: Date) {
        //set time
        checkInCardViewId.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.md_amber_400))
        //change color or image
        checkInDateAmTextId.text = date.toString()
    }

    override fun setUICheckOutAm(date: Date) {
        //set time
        checkInCardViewId.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.md_brown_400))
        //change color or image
        checkOutDateAmTextId.text = date.toString()
    }

    override fun setUICheckInPm(date: Date) {
        //set time
        //change color or image
        checkOutCardViewId.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.md_teal_400))
        checkInDatePmTextId.text = date.toString()
    }

    override fun setUICheckOutPm(date: Date) {
        //set time
        //change color or image
        checkOutCardViewId.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.md_pink_400))
        checkOutDatePmTextId.text = date.toString()
    }

    companion object {
        lateinit var component : TimeSchedulePersistenceComponent
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
