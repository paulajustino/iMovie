package com.example.imovie.presentation.view

import android.content.Context
import androidx.fragment.app.Fragment

fun Fragment.statusBarHeightOverCard() : Int = context?.statusBarHeight() ?: 0

fun Context.statusBarHeight(): Int {
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId <= 0) {
        return 0
    }
    return resources.getDimensionPixelSize(resourceId)
}