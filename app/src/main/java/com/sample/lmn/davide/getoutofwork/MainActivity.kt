package com.sample.lmn.davide.getoutofwork
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.sample.lmn.davide.getoutofwork.components.DaggerTimeSchedulePersistenceComponent
import com.sample.lmn.davide.getoutofwork.components.TimeSchedulePersistenceComponent
import com.sample.lmn.davide.getoutofwork.managers.RealmPersistenceManager
import com.sample.lmn.davide.getoutofwork.modules.RealmPersistenceModule
import com.sample.lmn.davide.getoutofwork.presenters.TimeScheduleRegisterPresenter
import com.sample.lmn.davide.getoutofwork.presenters.isAm
import com.sample.lmn.davide.getoutofwork.services.RealTimeBackgroundService
import com.sample.lmn.davide.getoutofwork.views.TimeScheduleRegisterView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), TimeScheduleRegisterView {
    val connection: LocalServiceConnection = LocalServiceConnection()

    val component : TimeSchedulePersistenceComponent by lazy {
        DaggerTimeSchedulePersistenceComponent
                .builder()
                .realmPersistenceModule(RealmPersistenceModule(applicationContext))
                .build()
    }

    val presenter: TimeScheduleRegisterPresenter by lazy {
        TimeScheduleRegisterPresenter(this, timeSchedulePersistenceManager)
    }

    @Inject lateinit var timeSchedulePersistenceManager: RealmPersistenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //inject component dagger
        component.inject(this)

        //bind service
        bindService(Intent(this, RealTimeBackgroundService::class.java), connection,
                Context.BIND_AUTO_CREATE)

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
    private fun onInitView() {
        //TODO to be removed
        dateTimeLabelId.text = if (Date().isAm()) "Morning" else "Afternoon"

        presenter.initView()
        historyCheckCardviewId.setClockOutTime(presenter.getClockOutDate())
        checkInCardviewId.setOnClickListener { presenter.setCheckIn() }
        checkOutCardviewId.setOnClickListener { presenter.setCheckOut() }
    }

    /**
     * set ui
     */
    override fun setUICheckInAm(date: Date) {
        checkInCardviewId.setCheckDate(date, R.color.md_amber_400)
        historyCheckCardviewId.setCheckIn(date)
    }

    /**
     * set ui
     */
    override fun setUICheckOutAm(date: Date) {
        checkOutCardviewId.setCheckDate(date, R.color.md_brown_400)
        historyCheckCardviewId.setCheckOut(date)
    }

    /**
     * set ui
     */
    override fun setUICheckInPm(date: Date) {
        checkInCardviewId.setCheckDate(date, R.color.md_teal_400)
    }

    /**
     * set ui
     */
    override fun setUICheckOutPm(date: Date) {
        checkOutCardviewId.setCheckDate(date, R.color.md_pink_400)
    }

    /**
     * set ui
     */
    override fun showErrorUI(dateTime: Int) {
        Snackbar.make(mainViewLayoutId, "${getString(R.string.generic_error)} at " +
                " $dateTime", Snackbar.LENGTH_SHORT).show()
    }


    /**
     * service connection to handle change background depending on time
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
