package com.example.imovie.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imovie.Movie
import com.example.imovie.Section
import com.example.imovie.domain.usecase.GetHomeListUseCase
import com.example.imovie.utils.Result
import kotlinx.coroutines.launch

sealed class HomeResult {
    object Loading : HomeResult()
    object Error : HomeResult()

    data class Success(val sections: List<Section>) : HomeResult()
}

class HomeViewModel : ViewModel() {

    private val getHomeListUseCase: GetHomeListUseCase = GetHomeListUseCase()

    val homeResult = MutableLiveData<HomeResult>()
    val homeHeaderResult = MutableLiveData<Movie>()

    fun fetch() {
        getHomeList()
    }

    private fun getHomeList() {
        homeResult.value = HomeResult.Loading
        viewModelScope.launch {
            val result = getHomeListUseCase.getHomeList()

            homeResult.value = when (result) {
                is Result.Success -> {
                    setHeaderMovie(result.value.random().listMovies.random())
                    HomeResult.Success(result.value)
                }
                is Result.Error -> HomeResult.Error
            }
        }
    }

    private fun setHeaderMovie(movie: Movie) {
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