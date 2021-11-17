package com.example.imovie.presentation.viewmodel

import com.example.imovie.presentation.LoginViewAction
import com.example.imovie.presentation.LoginViewState
import com.example.imovie.utils.BaseViewModel
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    override val viewState: LoginViewState
) : BaseViewModel<LoginViewState, LoginViewAction>() {

    override fun dispatchViewAction(viewAction: LoginViewAction) {
        TODO("Not yet implemented")
    }
}
