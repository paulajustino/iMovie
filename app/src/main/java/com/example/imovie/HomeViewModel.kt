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

    private val homeUseCase: HomeUseCase = HomeUseCase()

    val homeResult = MutableLiveData<HomeResult>()

    fun fetch() {
        getPopularMovies()
    }

    private fun getPopularMovies() {
        homeResult.value = HomeResult.Loading
        viewModelScope.launch {
            val result = homeUseCase.getHomeList()

            homeResult.value = when (result) {
                is Result.Success -> {
                    HomeResult.Success(result.value)
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