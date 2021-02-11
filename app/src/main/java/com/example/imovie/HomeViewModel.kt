package com.example.imovie

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

sealed class HomeResult {
    object Loading : HomeResult()
    object Error : HomeResult()

    data class Success(val sections : List<Section>) : HomeResult()
}

class HomeViewModel : ViewModel() {

    private val theMovieDbUseCase: TheMovieDbUseCase = TheMovieDbUseCase()

    val homeResult = MutableLiveData<HomeResult>()

    fun fetch() {
        getPopularMovies()
    }

    private fun getPopularMovies() {
        homeResult.value = HomeResult.Loading
        viewModelScope.launch {
            val result = theMovieDbUseCase.getPopularMovies()

            homeResult.value = when (result) {
                is Result.Success -> {
                    val popularMovieSection = Section("1", "Filmes Populares", result.value)
                    HomeResult.Success(listOf(popularMovieSection))
                }
                is Result.Error -> HomeResult.Error
            }
        }
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