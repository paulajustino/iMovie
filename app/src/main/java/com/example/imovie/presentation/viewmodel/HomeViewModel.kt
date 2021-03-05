package com.example.imovie.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imovie.MovieUiModel
import com.example.imovie.SectionUiModel
import com.example.imovie.domain.usecase.GetHomeListUseCase
import com.example.imovie.presentation.mapper.MovieModelToUiModelMapper
import com.example.imovie.presentation.mapper.SectionModelToUiModelMapper
import com.example.imovie.utils.Result
import com.example.imovie.utils.randomOrNull
import kotlinx.coroutines.launch

sealed class HomeResult {
    object Loading : HomeResult()
    object Error : HomeResult()

    data class Success(val sections: List<SectionUiModel>) : HomeResult()
}

class HomeViewModel constructor(
    private val getHomeListUseCase: GetHomeListUseCase = GetHomeListUseCase(),
    private val movieUiModelMapper: MovieModelToUiModelMapper = MovieModelToUiModelMapper(),
    private val sectionUiModelMapper: SectionModelToUiModelMapper = SectionModelToUiModelMapper()
) : ViewModel() {

    val homeResult = MutableLiveData<HomeResult>()
    val homeHeaderResult = MutableLiveData<MovieUiModel>()

    fun fetch() {
        getHomeList()
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

    fun addFavorite() {
        addFavoriteMovies()
    }

    private fun addFavoriteMovies() {
        Log.i("HomeViewModel", "addFavoriteMoviesButton clicked")
    }

    fun details() {
        movieDetails()
    }

    private fun movieDetails() {
        Log.i("HomeViewModel", "movieDetailsButton clicked")
    }
}