package com.example.imovie.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
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

    val detailsResult = MutableLiveData<DetailsResult>()
    val similarResult = MutableLiveData<SimilarResult>()

    override fun dispatchViewAction(viewAction: DetailsViewAction) {
        when (viewAction) {
            is DetailsViewAction.OnDetailsInitialized -> getMovieDetails(viewAction.movieId)
        }
    }

    private fun getMovieDetails(id: String) {
        detailsResult.value = DetailsResult.Loading
        similarResult.value = SimilarResult.Loading
        viewModelScope.launch {
            val similar = getSimilarUseCase.getSimilarMovies(id)
            val details = getDetailsUseCase.getDetails(id)

            detailsResult.value = when (details) {
                is Result.Success -> {
                    DetailsResult.Success(movieDetailsUiModelMapper.mapFrom(details.value))
                }
                is Result.Error -> DetailsResult.Error
            }

            similarResult.value = when (similar) {
                is Result.Success -> {
                    similar.value.filter { it.posterPath != null }
                    SimilarResult.Success(movieListUiModelMapper.mapListFrom(similar.value))
                }
                is Result.Error -> SimilarResult.Error
            }
        }
    }
}