package com.example.imovie.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imovie.presentation.model.MovieDetailsUiModel
import com.example.imovie.domain.usecase.GetDetailsUseCase
import com.example.imovie.presentation.DetailsViewAction
import com.example.imovie.presentation.DetailsViewState
import com.example.imovie.presentation.HomeViewAction
import com.example.imovie.presentation.mapper.MovieDetailsModelToUiModelMapper
import com.example.imovie.utils.BaseViewModel
import com.example.imovie.utils.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class DetailsResult {
    object Loading : DetailsResult()
    object Error : DetailsResult()

    data class Success(val movie: MovieDetailsUiModel) : DetailsResult()
}

class DetailsViewModel @Inject constructor(
    private val getDetailsUseCase: GetDetailsUseCase,
    private val movieDetailsUiModelMapper: MovieDetailsModelToUiModelMapper,
    override val viewState: DetailsViewState
) : BaseViewModel<DetailsViewState, DetailsViewAction>() {

    val detailsResult = MutableLiveData<DetailsResult>()

    override fun dispatchViewAction(viewAction: DetailsViewAction) {
        when (viewAction) {
            is DetailsViewAction.OnDetailsInitialized -> getMovieDetails(viewAction.movieId)
        }
    }

    private fun getMovieDetails(id: String) {
        detailsResult.value = DetailsResult.Loading
        viewModelScope.launch {
            val result = getDetailsUseCase.getDetails(id)

            detailsResult.value = when (result) {
                is Result.Success -> {
                    DetailsResult.Success(movieDetailsUiModelMapper.mapFrom(result.value))
                }
                is Result.Error -> DetailsResult.Error
            }
        }
    }
}