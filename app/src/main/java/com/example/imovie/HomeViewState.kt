package com.example.imovie

import javax.inject.Inject

class HomeViewState @Inject constructor() {

    val action: SingleLiveEvent<Action> = SingleLiveEvent()

    sealed class Action {
        data class OpenDetails(val id: String) : Action()

        object OpenFavorites : Action()
    }
}