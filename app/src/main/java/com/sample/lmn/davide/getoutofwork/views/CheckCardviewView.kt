package com.sample.lmn.davide.getoutofwork.views

import android.content.Context
import android.support.annotation.AttrRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.View
import com.sample.lmn.davide.getoutofwork.R
import com.sample.lmn.davide.getoutofwork.presenters.italianFormat
import kotlinx.android.synthetic.main.check_cardview_layout.view.*
import java.util.*

/**
 * Created by davide-syn on 7/5/17.
 */
class CheckCardviewView : CardView {
    constructor(context: Context) : super(context) { inflateLayout() }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {inflateLayout()}

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(context, attrs, defStyleAttr) {inflateLayout()}

    fun inflateLayout() {
        View.inflate(context, R.layout.check_cardview_layout, this)
    }

    fun setBackgroundColorByRes(color: Int) { setBackgroundColor(ContextCompat.getColor(context, color)) }

    fun setOutAmLayout(date: Date?) {
        setCardLayout(date, R.drawable.character)
    }

    fun setInAmLayout(date: Date?) {
        setCardLayout(date, R.drawable.character)
    }

    fun setInPmLayout(date: Date?) {
        setCardLayout(date, R.drawable.character)
    }

    fun setOutPmLayout(date: Date?) {
        setCardLayout(date, R.drawable.character)
    }

    /**
     *
     */
    private fun setCardLayout(date: Date?, resourceDrawableId : Int) {
        checkDateTextId.text = date?.italianFormat()
//        checkImageViewId.setImageDrawable(ContextCompat.getDrawable(context, resourceDrawableId))
        setBackgroundColorByRes(R.color.md_blue_grey_800)
        checkDateTextId.setTextColor(ContextCompat.getColor(context, R.color.md_grey_50))
    }
}
