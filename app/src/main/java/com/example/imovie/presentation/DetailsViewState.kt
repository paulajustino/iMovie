package com.example.imovie.presentation

import com.example.imovie.utils.SingleLiveEvent
import javax.inject.Inject

class DetailsViewState @Inject constructor() {

    val action: SingleLiveEvent<Action> = SingleLiveEvent()

    sealed class Action {
        data class OpenSimilarMovieDetails(val id: String) : Action()

        object OpenFavorites : Action()

        object Share : Action()
    }
}