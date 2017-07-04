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
    lateinit var alarmManager :AlarmManager
    lateinit var intent: Intent
    lateinit var pendingIntent: PendingIntent

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
        alarmManager = getSystemService(android.content.Context.ALARM_SERVICE) as android.app.AlarmManager
        intent = Intent(this, AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
    }

    class AlarmReceiver: BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
        }
    }
}
