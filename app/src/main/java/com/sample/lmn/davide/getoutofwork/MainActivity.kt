package com.sample.lmn.davide.getoutofwork
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.sample.lmn.davide.getoutofwork.managers.RealmPersistenceManager
import com.sample.lmn.davide.getoutofwork.models.OutInEnum
import com.sample.lmn.davide.getoutofwork.models.TimeSchedule
import com.sample.lmn.davide.getoutofwork.presenters.TimeScheduleRegisterPresenter
import com.sample.lmn.davide.getoutofwork.presenters.isAm
import com.sample.lmn.davide.getoutofwork.services.RealTimeBackgroundService
import com.sample.lmn.davide.getoutofwork.views.TimeScheduleRegisterView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), TimeScheduleRegisterView {
    private val connection: LocalServiceConnection = LocalServiceConnection()
    private val presenter: TimeScheduleRegisterPresenter by lazy {
        TimeScheduleRegisterPresenter(this, timeSchedulePersistenceManager)
    }

    private val timeSchedulePersistenceManager by lazy {
        RealmPersistenceManager.Holder(applicationContext).instance
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        onInitService()
        onInitView()
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
    }

    /**
     * init view
     */
    private fun onInitService() {
        bindService(Intent(this, RealTimeBackgroundService::class.java), connection,
                Context.BIND_AUTO_CREATE)
    }

    /**
     *
     */
    private fun onInitView() {
        presenter.initView()
        dateTimeLabelId.text = if (Date().isAm()) "Morning" else "Afternoon"
//        historyCheckCardviewId.init(presenter.getClockOutDate(), presenter.getClockToday())
        checkCardviewId.setOnClickListener { presenter.setCheck() }
    }

    /**
     * move in presenter
     */
    override fun updateCheckCardview(timeSchedule: TimeSchedule) {
        timeSchedule.apply {
            when {
                ((getCheck() == OutInEnum.IN) and (dateTime == Calendar.AM)) -> checkCardviewId.setInAmLayout(currentCheckedDate)
                ((getCheck() == OutInEnum.OUT) and (dateTime == Calendar.AM)) -> checkCardviewId.setOutAmLayout(currentCheckedDate)
                ((getCheck() == OutInEnum.IN) and (dateTime == Calendar.PM)) -> checkCardviewId.setInPmLayout(currentCheckedDate)
                ((getCheck() == OutInEnum.OUT) and (dateTime == Calendar.PM)) -> checkCardviewId.setOutPmLayout(currentCheckedDate)
            }
        }
    }

    /**
     * set clock out time
     */
    override fun setClockOutTime(date: Date?) {
//        historyCheckCardviewId.setClockOutTime(date)
    }

    /**
     * set ui
     */
    override fun showErrorUI(message: String) {
        Snackbar.make(mainViewLayoutId,
                "${getString(R.string.generic_error)} at $message", Snackbar.LENGTH_SHORT).show()
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