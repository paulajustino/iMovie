package com.example.imovie

import androidx.lifecycle.ViewModel

abstract class BaseViewModel<T, A> : ViewModel() {

    abstract val viewState: T

    abstract fun dispatchViewAction(viewAction : A)
}