package com.example.imovie.presentation

import com.example.imovie.utils.SingleLiveEvent
import javax.inject.Inject

class HomeViewState @Inject constructor() {

    val action: SingleLiveEvent<Action> = SingleLiveEvent()

    sealed class Action {
        data class OpenDetails(val id: String) : Action()

        object OpenFavorites : Action()
    }
}