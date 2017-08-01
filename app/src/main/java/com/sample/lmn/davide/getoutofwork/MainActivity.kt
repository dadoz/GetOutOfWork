package com.sample.lmn.davide.getoutofwork
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.sample.lmn.davide.getoutofwork.managers.RealmPersistenceManager
import com.sample.lmn.davide.getoutofwork.presenters.OutInEnum
import com.sample.lmn.davide.getoutofwork.presenters.TimeScheduleRegisterPresenter
import com.sample.lmn.davide.getoutofwork.presenters.isAm
import com.sample.lmn.davide.getoutofwork.services.RealTimeBackgroundService
import com.sample.lmn.davide.getoutofwork.views.TimeScheduleRegisterView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), TimeScheduleRegisterView {

    val connection: LocalServiceConnection = LocalServiceConnection()

    val presenter: TimeScheduleRegisterPresenter by lazy {
        TimeScheduleRegisterPresenter(this, timeSchedulePersistenceManager)
    }

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
    }

    /**
     *
     */
    private fun onInitView() {
        //TODO to be removed
        dateTimeLabelId.text = if (Date().isAm()) "Morning" else "Afternoon"

        presenter.initView()
        historyCheckCardviewId.init(presenter.getClockOutDate(), presenter.getClockToday())
        checkCardviewId.setOnClickListener { presenter.setCheck() }
    }

    /**
     *
     */
    override fun updateCheckCardview(date: Date, check: OutInEnum, dateTime: Int) {
        if (check == OutInEnum.IN && dateTime == Calendar.AM)
            checkCardviewId.setInAmLayout(date)
        if (check == OutInEnum.IN && dateTime == Calendar.AM)
            checkCardviewId.setOutAmLayout(date)
        if (check == OutInEnum.IN && dateTime == Calendar.AM)
            checkCardviewId.setInPmLayout(date)
        if (check == OutInEnum.IN && dateTime == Calendar.AM)
            checkCardviewId.setOutPmLayout(date)
    }

    /**
     * set clock out time
     */
    override fun setClockOutTime(date: Date) {
        historyCheckCardviewId.setClockOutTime(date)
    }

    /**
     * set ui
     */
    override fun showErrorUI(dateTime: Int) {
        val snackbar = Snackbar.make(mainViewLayoutId, "${getString(R.string.generic_error)} at " +
                " $dateTime", Snackbar.LENGTH_SHORT)
        snackbar.view.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.md_red_400))
        snackbar.show()
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