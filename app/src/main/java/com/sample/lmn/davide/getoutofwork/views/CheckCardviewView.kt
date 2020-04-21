package com.sample.lmn.davide.getoutofwork.views

import android.content.Context
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import androidx.annotation.AttrRes
import androidx.cardview.widget.CardView
import com.google.android.material.card.MaterialCardView
import com.sample.lmn.davide.getoutofwork.R
import com.sample.lmn.davide.getoutofwork.presenters.italianFormat
import kotlinx.android.synthetic.main.check_cardview_layout.view.*
import java.util.*

/**
 * Created by davide-syn on 7/5/17.
 */
class CheckCardviewView : MaterialCardView {
    constructor(context: Context) : super(context) { inflateLayout() }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {inflateLayout()}
    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(context, attrs, defStyleAttr) {inflateLayout()}

    private fun inflateLayout() {
        View.inflate(context, R.layout.check_cardview_layout, this)
    }

    fun setBackgroundColorByRes(color: Int) { setBackgroundColor(ContextCompat.getColor(context, color)) }

    fun setOutAmLayout(date: Date?) {
        setCardLayout(date, null, null)
    }

    fun setInAmLayout(date: Date?) {
        setCardLayout(date, R.drawable.cat_coffee_col_white, "tap to start your work day")
    }

    fun setInPmLayout(date: Date?) {
        setCardLayout(date, null, null)
    }

    fun setOutPmLayout(date: Date?) {
        setCardLayout(date, null, null)
    }

    /**
     *
     */
    private fun setCardLayout(date: Date?, resourceDrawableId : Int?, message: String ?) {
        checkDateTextId.text = date?.italianFormat()?: " - "
        resourceDrawableId?.let {
            checkImageViewId.setImageDrawable(ContextCompat.getDrawable(context, resourceDrawableId))
        }
        checkButtonStatusTextId.text = message
//        setBackgroundColorByRes(R.color.md_blue_grey_800)
//        checkDateTextId.setTextColor(ContextCompat.getColor(context, R.color.md_grey_50))
    }
}
