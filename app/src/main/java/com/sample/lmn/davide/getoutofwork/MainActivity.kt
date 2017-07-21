package com.sample.lmn.davide.getoutofwork

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.sample.lmn.davide.getoutofwork.components.TimeSchedulePersistenceComponent
import com.sample.lmn.davide.getoutofwork.managers.RealmPersistenceManager
import com.sample.lmn.davide.getoutofwork.presenters.TimeScheduleRegisterPresenter
import com.sample.lmn.davide.getoutofwork.services.RealTimeBackgroundService
import com.sample.lmn.davide.getoutofwork.views.TimeScheduleRegisterView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), TimeScheduleRegisterView {

    val connection: LocalServiceConnection = LocalServiceConnection()
    lateinit var presenter: TimeScheduleRegisterPresenter

    lateinit var timeSchedulePersistenceManager: RealmPersistenceManager

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
        //bind service
        bindService(Intent(this, RealTimeBackgroundService::class.java), connection,
                Context.BIND_AUTO_CREATE)

        timeSchedulePersistenceManager = RealmPersistenceManager.Holder(applicationContext).instance

        presenter = TimeScheduleRegisterPresenter(this, timeSchedulePersistenceManager)
    }

    /**
     *
     */
    private fun onInitView() {
        tagCardviewAmId.setOnClickListener { presenter.setCheckInAm() }
        tagCardviewPmId.setOnClickListener { presenter.setCheckInPm() }
    }

    /**
     *
     */
    override fun setUICheckInAm(date: Date) {
        tagCardviewAmId.setBackgroundColorByRes(R.color.md_amber_400)
//        tagCardviewAmId.setCheckInDate(date)
    }

    override fun setUICheckOutAm(date: Date) {
        tagCardviewAmId.setBackgroundColorByRes(R.color.md_brown_400)
//        tagCardviewAmId.setCheckOutDate(date)
    }

    override fun setUICheckInPm(date: Date) {
        tagCardviewPmId.setBackgroundColorByRes(R.color.md_teal_400)
//        tagCardviewPmId.setCheckInDate(date)
    }

    override fun setUICheckOutPm(date: Date) {
        tagCardviewPmId.setBackgroundColorByRes(R.color.md_pink_400)
//        tagCardviewPmId.setCheckOutDate(date)
    }

    override fun showErrorUI(dateTime: Int) {
        Snackbar.make(mainViewLayoutId, getString(R.string.generic_error) + dateTime, Snackbar.LENGTH_SHORT).show()
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



