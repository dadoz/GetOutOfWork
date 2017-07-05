package com.sample.lmn.davide.getoutofwork.views

import android.content.Context
import android.support.annotation.AttrRes
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.sample.lmn.davide.getoutofwork.R
import kotlinx.android.synthetic.main.tag_cardview_layout.view.*
import java.util.*

/**
 * Created by davide-syn on 7/5/17.
 */

class TagCardviewView : FrameLayout {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    init {
        View.inflate(context, R.layout.tag_cardview_layout, this)
    }

    fun setBackgroundColorByRes(color: Int) { checkCardviewId.setBackgroundColor(ContextCompat.getColor(context, color)) }
    fun setCheckInDate(date :Date) { checkInDateTextId.text = date.toString() }

    fun setCheckOutDate(date :Date) { checkOutDateTextId.text = date.toString() }
}
