package com.example.imovie.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.imovie.domain.usecase.GetHomeListUseCase
import com.example.imovie.presentation.HomeViewAction
import com.example.imovie.presentation.HomeViewState
import com.example.imovie.presentation.mapper.MovieModelToUiModelMapper
import com.example.imovie.presentation.mapper.SectionModelToUiModelMapper
import com.example.imovie.presentation.model.MovieUiModel
import com.example.imovie.presentation.model.SectionUiModel
import com.example.imovie.utils.BaseViewModel
import com.example.imovie.utils.Result
import com.example.imovie.utils.randomOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class HomeResult {
    object Loading : HomeResult()
    object Error : HomeResult()

    data class Success(val sections: List<SectionUiModel>) : HomeResult()
}

class HomeViewModel @Inject constructor(
    private val getHomeListUseCase: GetHomeListUseCase,
    private val movieUiModelMapper: MovieModelToUiModelMapper,
    private val sectionUiModelMapper: SectionModelToUiModelMapper,
    override val viewState: HomeViewState
) : BaseViewModel<HomeViewState, HomeViewAction>() {

    val homeResult = MutableLiveData<HomeResult>()
    val homeHeaderResult = MutableLiveData<MovieUiModel>()

    override fun dispatchViewAction(viewAction: HomeViewAction) {
        when (viewAction) {
            is HomeViewAction.OnHomeMovieClicked -> openHeaderDetails()
            is HomeViewAction.OnCarouselHomeMovieClicked -> openDetails(viewAction.movieId)
            is HomeViewAction.OnHomeInitialized -> getHomeList()
            is HomeViewAction.OnFavoriteMoviesClicked -> addFavoriteMovies()
        }
    }

    private fun getHomeList() {
        homeResult.value = HomeResult.Loading
        viewModelScope.launch {
            val result = getHomeListUseCase.getHomeList()

            homeResult.value = when (result) {
                is Result.Success -> {
                    result.value.filter { it.listMovies.isNotEmpty() }
                        .randomOrNull()?.listMovies?.randomOrNull()?.let {
                            setHeaderMovie(movieUiModelMapper.mapFrom(it))
                        }

                    HomeResult.Success(sectionUiModelMapper.mapFrom(result.value))
                }
                is Result.Error -> HomeResult.Error
            }
        }
    }

    private fun setHeaderMovie(movie: MovieUiModel) {
        homeHeaderResult.value = movie
    }

    private fun addFavoriteMovies() {
        viewState.action.value = HomeViewState.Action.OpenFavorites
    }

    private fun openHeaderDetails() {
        val movieId = this.homeHeaderResult.value?.id
        if (!movieId.isNullOrEmpty()) {
            viewState.action.value = HomeViewState.Action.OpenDetails(movieId)
        }
    }

    private fun openDetails(id: String) {
        if (!id.isNullOrEmpty()) {
            viewState.action.value = HomeViewState.Action.OpenDetails(id)
        }
    }

}