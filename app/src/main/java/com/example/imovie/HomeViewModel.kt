package com.example.imovie

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imovie.service.MovieResponse
import com.example.imovie.service.Response as ResponseAll
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

sealed class HomeResult {
    object Loading : HomeResult()
    object Error : HomeResult()

    data class Success(val sections : List<Section>) : HomeResult()
}

class HomeViewModel : ViewModel() {

    val homeResult = MutableLiveData<HomeResult>()
    private val _sections = MutableLiveData<List<Section>>()
    val sections: LiveData<List<Section>>
        get() = _sections

    fun fetch() {
        getPopularMovies()
    }

    private fun getPopularMovies() {
        homeResult.value = HomeResult.Loading
        TheMovieDbApi.retrofitService.getPopularMovies("54c52ec7a5a35959c73721dc3b8dbf25", "pt-BR", "1").enqueue( object: Callback<ResponseAll> {

            override fun onFailure(call: Call<ResponseAll>, t: Throwable) {
                homeResult.value = HomeResult.Error
                Log.d("HomeViewModel", "falha no request ${t.message}")
            }

            override fun onResponse(call: Call<ResponseAll>, response: Response<ResponseAll>) {
                if(response.isSuccessful) {
                    onSuccess(response.body()?.results.orEmpty())
                } else {
                    Log.d("HomeViewModel", "falha no request")
                }
            }
        })
    }

    private fun onSuccess(movies: List<MovieResponse>) {

        val movieList = movies.map {
            Movie(
                id = "${it.id}",
                titleMovie = it.title,
                imageUrl = "https://image.tmdb.org/t/p/w92" + it.posterPath,
                descriptionMovie = it.overview
            )
        }

        val section = Section(
            id = "1",
            titleSection = "Filmes Populares",
            listMovies = movieList
        )

        _sections.value = listOf(section)
        homeResult.value = HomeResult.Success(listOf(section))
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