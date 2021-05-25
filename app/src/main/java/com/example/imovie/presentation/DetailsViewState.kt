package com.example.imovie.presentation

import androidx.lifecycle.MutableLiveData
import com.example.imovie.presentation.viewmodel.DetailsResult
import com.example.imovie.presentation.viewmodel.SimilarResult
import com.example.imovie.utils.SingleLiveEvent
import javax.inject.Inject

class DetailsViewState @Inject constructor() {

    val action: SingleLiveEvent<Action> = SingleLiveEvent()
    val detailsResult = MutableLiveData<DetailsResult>()
    val similarResult = MutableLiveData<SimilarResult>()

    sealed class Action {
        data class OpenSimilarMovieDetails(val id: String) : Action()

        object OpenFavorites : Action()

        object Share : Action()
    }
}