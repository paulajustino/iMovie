package com.example.imovie.data.remote

import com.example.imovie.Movie
import com.example.imovie.NetworkError
import com.example.imovie.data.api.TheMovieDbApi
import com.example.imovie.data.mapper.MovieListResponseToMovieModelMapper
import com.example.imovie.utils.Constants
import com.example.imovie.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TheMovieDbRemoteDataSource {
    private val theMovieApi: TheMovieDbApi = TheMovieDbApi
    private val movieListMapper: MovieListResponseToMovieModelMapper = MovieListResponseToMovieModelMapper()

    suspend fun getPopularMovies(): Result<List<Movie>, NetworkError> {
        return withContext(Dispatchers.IO) {
            val response = theMovieApi.retrofitService.getPopularMovies(Constants.API_KEY, Constants.LANGUAGE)
            if (response.isSuccessful) {
                val data = movieListMapper.mapListFrom(response.body()).filter { it.posterPath != null }
                Result.Success(data)
            } else {
                Result.Error(NetworkError())
            }
        }
    }

    suspend fun getNowPlayingMovies(): Result<List<Movie>, NetworkError> {
        return withContext(Dispatchers.IO) {
            val response = theMovieApi.retrofitService.getNowPlayingMovies(Constants.API_KEY, Constants.LANGUAGE)
            if (response.isSuccessful) {
                val data = movieListMapper.mapListFrom(response.body()).filter { it.posterPath != null }
                Result.Success(data)
            } else {
                Result.Error(NetworkError())
            }
        }
    }

    suspend fun getTopRatedMovies(): Result<List<Movie>, NetworkError> {
        return withContext(Dispatchers.IO) {
            val response = theMovieApi.retrofitService.getTopRatedMovies(Constants.API_KEY, Constants.LANGUAGE)
            if (response.isSuccessful) {
                val data = movieListMapper.mapListFrom(response.body()).filter { it.posterPath != null }
                Result.Success(data)
            } else {
                Result.Error(NetworkError())
            }
        }
    }

    suspend fun getUpcomingMovies(): Result<List<Movie>, NetworkError> {
        return withContext(Dispatchers.IO) {
            val response = theMovieApi.retrofitService.getUpcomingMovies(Constants.API_KEY, Constants.LANGUAGE)
            if (response.isSuccessful) {
                val data = movieListMapper.mapListFrom(response.body()).filter { it.posterPath != null }
                Result.Success(data)
            } else {
                Result.Error(NetworkError())
            }
        }
    }
}