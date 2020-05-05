package com.sample.lmn.davide.getoutofwork.ui.views

import android.content.Context
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import androidx.annotation.AttrRes
import com.google.android.material.card.MaterialCardView
import com.sample.lmn.davide.getoutofwork.R
import com.sample.lmn.davide.getoutofwork.models.CheckTypeEnum
import kotlinx.android.synthetic.main.check_time_tot_cardview_layout.view.*
import java.util.*

/**
 * Created by davide-syn on 7/5/17.
 */
class CheckTimeTotCardView : MaterialCardView {
    constructor(context: Context) : super(context) { inflateLayout() }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {inflateLayout()}
    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(context, attrs, defStyleAttr) {inflateLayout()}

    private fun inflateLayout() {
        View.inflate(context, R.layout.check_time_tot_cardview_layout, this)
    }

    fun setTotHours(hours: Date) {
        checkTimeTotHoursTextId.text = hours.toString()
    }
    fun setType(checkType: CheckTypeEnum) {
        checkTimeTypeTextId.text = checkType.name
    }
}
