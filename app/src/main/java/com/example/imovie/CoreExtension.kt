package com.example.imovie

import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop

fun View.setMargin(left: Int? = null, top: Int? = null, right: Int? = null, bottom: Int? = null) {
    val params = (layoutParams as? ViewGroup.MarginLayoutParams)
    params?.apply {
        setMargins(
            left ?: leftMargin,
            top ?: topMargin,
            right ?: rightMargin,
            bottom ?: bottomMargin
        )
        this@setMargin.layoutParams = this
    }
}
fun View.addMarginTop(margin: Int) {
    this.setMargin(this.marginLeft, this.marginTop + margin, this.marginRight, this.marginBottom)
}