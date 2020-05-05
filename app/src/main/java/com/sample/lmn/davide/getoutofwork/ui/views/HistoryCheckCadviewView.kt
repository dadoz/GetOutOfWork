package com.sample.lmn.davide.getoutofwork.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.AttrRes
import com.google.android.material.card.MaterialCardView
import com.sample.lmn.davide.getoutofwork.R
import com.sample.lmn.davide.getoutofwork.italianFormat
import com.sample.lmn.davide.getoutofwork.models.TimeSchedule
import kotlinx.android.synthetic.main.history_check_cardview_layout.view.*
import java.util.*

/**
 * Created by davide on 08/07/2017.
 */
class HistoryCheckCardviewView : MaterialCardView {
    constructor(context: Context) : super(context) {inflateLayout()}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {inflateLayout()}
    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(context, attrs, defStyleAttr) {inflateLayout()}

    private fun inflateLayout() {
        View.inflate(context, R.layout.history_check_cardview_layout, this)
    }

    fun setCheckIn(date: Date) {
        historyCheckInDateTextId.text = date.italianFormat()
    }
    fun setCheckOut(date: Date) {
        historyCheckOutDateTextId.text = date.italianFormat()
    }

    fun setTitle(title: String) {
        checkTimeTitleTextId.text = title
    }

    fun setClockOutTime(clockOutDate: Date?) {
//        checkTimeTitleTextId.text = clockOutDate?.timeFormat()?: " - "
    }

    fun setClockAm(today: TimeSchedule) {
        historyCheckInDateTextId.text = today.toString()//: context.getString(R.string.no_clock_am)
        historyCheckOutDateTextId.text = today.toString()//: context.getString(R.string.no_clock_am)
    }

    fun init(clockOutDate: Date?, clockToday: TimeSchedule) {
        setClockOutTime(clockOutDate)
        setClockAm(clockToday)
    }
}
