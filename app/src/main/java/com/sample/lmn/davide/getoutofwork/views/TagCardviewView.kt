package com.sample.lmn.davide.getoutofwork.views

import android.content.Context
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import com.sample.lmn.davide.getoutofwork.R
import kotlinx.android.synthetic.main.tag_cardview_layout.view.*
import java.util.*

/**
 * Created by davide-syn on 7/5/17.
 */

class TagCardviewView : FrameLayout {
    init {
        View.inflate(context, R.layout.tag_cardview_layout, this)
    }
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    fun setBackgroundColorByRes(color: Int) { checkCardviewId.setBackgroundColor(ContextCompat.getColor(context, color)) }

    fun setCheckDate(date :Date) { checkDateTextId.text = date.toString() }

}
