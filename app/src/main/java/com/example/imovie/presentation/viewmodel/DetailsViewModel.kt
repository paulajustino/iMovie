package com.example.imovie.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.imovie.domain.usecase.GetDetailsUseCase
import com.example.imovie.domain.usecase.GetSimilarMoviesUseCase
import com.example.imovie.presentation.DetailsViewAction
import com.example.imovie.presentation.DetailsViewState
import com.example.imovie.presentation.mapper.MovieDetailsModelToUiModelMapper
import com.example.imovie.presentation.mapper.MovieListModelToUiModelMapper
import com.example.imovie.presentation.model.MovieDetailsUiModel
import com.example.imovie.presentation.model.MovieUiModel
import com.example.imovie.utils.BaseViewModel
import com.example.imovie.utils.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class DetailsResult {
    object Loading : DetailsResult()
    object Error : DetailsResult()

    data class Success(val movie: MovieDetailsUiModel) : DetailsResult()
}

sealed class SimilarResult {
    object Loading : SimilarResult()
    object Error : SimilarResult()

    data class Success(val movie: List<MovieUiModel>) : SimilarResult()
}

class DetailsViewModel @Inject constructor(
    private val getDetailsUseCase: GetDetailsUseCase,
    private val getSimilarUseCase: GetSimilarMoviesUseCase,
    private val movieDetailsUiModelMapper: MovieDetailsModelToUiModelMapper,
    private val movieListUiModelMapper: MovieListModelToUiModelMapper,
    override val viewState: DetailsViewState
) : BaseViewModel<DetailsViewState, DetailsViewAction>() {

    override fun dispatchViewAction(viewAction: DetailsViewAction) {
        when (viewAction) {
            is DetailsViewAction.OnDetailsInitialized -> init(viewAction.movieId)
            is DetailsViewAction.OnSimilarMovieClicked -> openDetails(viewAction.movieId)
        }
    }

    private fun init(id: String) {
        getMovieDetails(id)
        getSimilarMovies(id)
    }

    private fun getSimilarMovies(id: String) {
        viewState.similarResult.value = SimilarResult.Loading
        viewModelScope.launch {
            val similar = getSimilarUseCase.getSimilarMovies(id)

            viewState.similarResult.value = when (similar) {
                is Result.Success -> {
                    val filteredList = similar.value.filter { it.posterPath != null }
                    SimilarResult.Success(movieListUiModelMapper.mapListFrom(filteredList))
                }
                is Result.Error -> SimilarResult.Error
            }
        }
    }

    private fun getMovieDetails(id: String) {
        viewState.detailsResult.value = DetailsResult.Loading
        viewModelScope.launch {
            val details = getDetailsUseCase.getDetails(id)

            viewState.detailsResult.value = when (details) {
                is Result.Success -> {
                    DetailsResult.Success(movieDetailsUiModelMapper.mapFrom(details.value))
                }
                is Result.Error -> DetailsResult.Error
            }
        }
    }

    private fun openDetails(id: String) {
        if (!id.isNullOrEmpty()) {
            viewState.action.value = DetailsViewState.Action.OpenSimilarMovieDetails(id)
        }
    }
}