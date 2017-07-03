package com.sample.lmn.davide.getoutofwork

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sample.lmn.davide.getoutofwork.views.TimeScheduleRegisterView

class MainActivity : AppCompatActivity(), TimeScheduleRegisterView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
