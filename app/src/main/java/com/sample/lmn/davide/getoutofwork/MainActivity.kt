package com.sample.lmn.davide.getoutofwork
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.sample.lmn.davide.getoutofwork.models.*
import com.sample.lmn.davide.getoutofwork.services.RealTimeBackgroundService
import com.sample.lmn.davide.getoutofwork.ui.views.TimeScheduleRegisterView
import com.sample.lmn.davide.getoutofwork.viewModels.RetrieveTimeScheduleViewModel
import khronos.Duration
import khronos.toString
import khronos.with
import kotlinx.android.synthetic.main.activity_main.*
import java.time.Instant.*
import java.util.*

class MainActivity : AppCompatActivity(), TimeScheduleRegisterView {
    private val connection: LocalServiceConnection = LocalServiceConnection()
    //    private val presenter: TimeScheduleRegisterPresenter by lazy {
//        TimeScheduleRegisterPresenter(this, timeSchedulePersistenceManager)
//    }
    val viewModel: RetrieveTimeScheduleViewModel by lazy {
        ViewModelProvider(this).get(RetrieveTimeScheduleViewModel::class.java)
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
//        presenter.initView()
//        historyCheckCardviewId.init(presenter.getClockOutDate(), presenter.getClockToday())
//        checkCardviewId.setOnClickListener { presenter.setCheck() }

        dateTimeLabelId.text = if (Date().isAm()) "Morning" else "Afternoon"

        viewModel.getTimeSchedule()

        viewModel.timeSchedule.observe(this, Observer {
            list -> Log.e("tag", "blalallallalllal --->" + list.size)
        })

        timeEnterCardId.apply {
            setTitle("Get in...")
            setOnClickListener {
                viewModel.addTimeSchedule(TimeSchedule(checkType= CheckTypeEnum.START.ordinal, dayTime = Calendar.AM, checkTime = Date()))
                        .observe(this@MainActivity, Observer{
                            res -> Log.e("tag", "blalaal"+res.size)
                        })

            }
        }

        timeExitCardId.apply {
            setTitle("Get out...")
            setOnClickListener {
                viewModel.addTimeSchedule(TimeSchedule(checkType = CheckTypeEnum.END.ordinal, dayTime = Calendar.AM, checkTime = Date()))
                        .observe(this@MainActivity, Observer{
                            res -> Log.e("tag", "blalaal"+res.size)
                        })
            }
        }

        setLaunchTimeCardId.apply {
            setTitle("At Launch")
            setOnClickListener {
                viewModel.addTimeSchedule(TimeSchedule(checkType= CheckTypeEnum.LAUNCH_START.ordinal, dayTime = Calendar.AM, checkTime = Date()))
                        .observe(this@MainActivity, Observer{
                            res -> Log.e("tag", "blalaal"+res.size)
                        })
            }
        }

        viewModel.timeSchedule.observe(this@MainActivity, Observer {
            list ->
            totHoursCardId.apply {
                this.setTotHours(list[list.size -1].checkTime)
                this.setType(CheckTypeEnum.values()[list[list.size -1].checkType])
            }
        })

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

    override fun updateCheckCardview(timeSchedule: TimeSchedule) {
        TODO("Not yet implemented")
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

/**
 * Date extensions
 */
fun Date.isAm(): Boolean = this.before(this.with(hour = 12))
fun Date.isPm(): Boolean = this.equals(this.with(hour = 12)) || this.after(this.with(hour = 12))
fun Date.italianFormat(): String = this.toString("HH:mm dd MMM")
fun Date.timeFormat(): String = this.toString("HH:mm")
fun Date.diffHours(date: Date?): Duration {
    val diff : Int = if (date != null) ((date.time - this.time) / (1000 * 60 * 60)).toInt() else 0
    return Duration(Calendar.HOUR_OF_DAY, diff + 1)
}