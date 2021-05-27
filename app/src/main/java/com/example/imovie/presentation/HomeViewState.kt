package com.example.imovie.presentation

import androidx.lifecycle.MutableLiveData
import com.example.imovie.presentation.model.MovieUiModel
import com.example.imovie.presentation.viewmodel.HomeResult
import com.example.imovie.utils.SingleLiveEvent
import javax.inject.Inject

class HomeViewState @Inject constructor() {

    val action: SingleLiveEvent<Action> = SingleLiveEvent()
    val homeResult = MutableLiveData<HomeResult>()
    val homeHeaderResult = MutableLiveData<MovieUiModel>()

    sealed class Action {
        data class OpenDetails(val id: String) : Action()

        object OpenFavorites : Action()
    }
}