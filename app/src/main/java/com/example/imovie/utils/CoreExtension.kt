package com.example.imovie.utils

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import com.bumptech.glide.Glide

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
fun ImageView.load(posterPath: String) {
    Glide.with(this)
            .load(ImageBuilder.build(posterPath, imagePresets()))
            .into(this)
}

fun View.imagePresets(): ImagePresets {
    return ImagePresets(width, height, context.applicationContext)
}

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()