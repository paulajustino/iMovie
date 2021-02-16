package com.example.imovie

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TheMovieDbRemoteDataSource {
    private val theMovieApi: TheMovieDbApi = TheMovieDbApi
    private val mapper: TheMovieDbResponseToModelMapper = TheMovieDbResponseToModelMapper()

    suspend fun getPopularMovies(): Result<List<Movie>, NetworkError> {
        return withContext(Dispatchers.IO) {
            val response = theMovieApi.retrofitService.getPopularMovies("54c52ec7a5a35959c73721dc3b8dbf25", "pt-BR", "1")
            if (response.isSuccessful) {
                val data = mapper.mapFrom(response.body())
                Result.Success(data)
            } else {
                Result.Error(NetworkError())
            }
        }
    }

    suspend fun getLatestMovies(): Result<List<Movie>, NetworkError> {
        return withContext(Dispatchers.IO) {
            val response = theMovieApi.retrofitService.getLatestMovies("54c52ec7a5a35959c73721dc3b8dbf25", "pt-BR")
            if (response.isSuccessful) {
                val data = mapper.mapFrom(response.body())
                Result.Success(data)
            } else {
                Result.Error(NetworkError())
            }
        }
    }

    suspend fun getNowPlayingMovies(): Result<List<Movie>, NetworkError> {
        return withContext(Dispatchers.IO) {
            val response = theMovieApi.retrofitService.getNowPlayingMovies("54c52ec7a5a35959c73721dc3b8dbf25", "pt-BR", "1")
            if (response.isSuccessful) {
                val data = mapper.mapFrom(response.body())
                Result.Success(data)
            } else {
                Result.Error(NetworkError())
            }
        }
    }

    suspend fun getTopRatedMovies(): Result<List<Movie>, NetworkError> {
        return withContext(Dispatchers.IO) {
            val response = theMovieApi.retrofitService.getTopRatedMovies("54c52ec7a5a35959c73721dc3b8dbf25", "pt-BR", "1")
            if (response.isSuccessful) {
                val data = mapper.mapFrom(response.body())
                Result.Success(data)
            } else {
                Result.Error(NetworkError())
            }
        }
    }

    suspend fun getUpcomingMovies(): Result<List<Movie>, NetworkError> {
        return withContext(Dispatchers.IO) {
            val response = theMovieApi.retrofitService.getUpcomingMovies("54c52ec7a5a35959c73721dc3b8dbf25", "pt-BR", "1")
            if (response.isSuccessful) {
                val data = mapper.mapFrom(response.body())
                Result.Success(data)
            } else {
                Result.Error(NetworkError())
            }
        }
    }
}