package com.sample.lmn.davide.getoutofwork.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder

/**
 * Created by davide-syn on 7/3/17.
 */

class RealTimeBackgroundService: Service() {
    val alarmManager :AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent: Intent = Intent(this, AlarmReceiver::class.java)
    val pendingIntent: PendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
    override fun onBind(p0: Intent?): IBinder = LocalBinder()

    class LocalBinder: Binder() {
        fun getService(): RealTimeBackgroundService {
            return RealTimeBackgroundService()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onCreate() {
        println("Service bind and started")

    }

    class AlarmReceiver: BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
        }
    }
}
