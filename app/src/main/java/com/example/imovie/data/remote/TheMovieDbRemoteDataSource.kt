package com.example.imovie.data.remote

import com.example.imovie.MovieModel
import com.example.imovie.NetworkError
import com.example.imovie.data.api.TheMovieDbApiService
import com.example.imovie.data.mapper.MovieListResponseToMovieModelMapper
import com.example.imovie.utils.Constants
import com.example.imovie.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface TheMovieDbRemoteDataSource {

    suspend fun getPopularMovies(): Result<List<MovieModel>, NetworkError>

    suspend fun getNowPlayingMovies(): Result<List<MovieModel>, NetworkError>

    suspend fun getTopRatedMovies(): Result<List<MovieModel>, NetworkError>

    suspend fun getUpcomingMovies(): Result<List<MovieModel>, NetworkError>
}

class TheMovieDbDefaultRemoteDataSource @Inject constructor(
    private val theMovieDbApiService: TheMovieDbApiService,
    private val movieListMapper: MovieListResponseToMovieModelMapper
) : TheMovieDbRemoteDataSource {

    override suspend fun getPopularMovies(): Result<List<MovieModel>, NetworkError> {
        return withContext(Dispatchers.IO) {
            val response =
                theMovieDbApiService.getPopularMovies(Constants.API_KEY, Constants.LANGUAGE)
            if (response.isSuccessful) {
                val data = movieListMapper.mapListFrom(response.body())
                Result.Success(data)
            } else {
                Result.Error(NetworkError())
            }
        }
    }

    override suspend fun getNowPlayingMovies(): Result<List<MovieModel>, NetworkError> {
        return withContext(Dispatchers.IO) {
            val response = theMovieDbApiService.getNowPlayingMovies(
                Constants.API_KEY,
                Constants.LANGUAGE
            )
            if (response.isSuccessful) {
                val data = movieListMapper.mapListFrom(response.body())
                Result.Success(data)
            } else {
                Result.Error(NetworkError())
            }
        }
    }

    override suspend fun getTopRatedMovies(): Result<List<MovieModel>, NetworkError> {
        return withContext(Dispatchers.IO) {
            val response =
                theMovieDbApiService.getTopRatedMovies(Constants.API_KEY, Constants.LANGUAGE)
            if (response.isSuccessful) {
                val data = movieListMapper.mapListFrom(response.body())
                Result.Success(data)
            } else {
                Result.Error(NetworkError())
            }
        }
    }

    override suspend fun getUpcomingMovies(): Result<List<MovieModel>, NetworkError> {
        return withContext(Dispatchers.IO) {
            val response =
                theMovieDbApiService.getUpcomingMovies(Constants.API_KEY, Constants.LANGUAGE)
            if (response.isSuccessful) {
                val data = movieListMapper.mapListFrom(response.body())
                Result.Success(data)
            } else {
                Result.Error(NetworkError())
            }
        }
    }
}