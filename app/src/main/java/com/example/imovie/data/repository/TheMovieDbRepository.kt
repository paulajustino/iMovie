package com.example.imovie.data.repository

import com.example.imovie.Movie
import com.example.imovie.NetworkError
import com.example.imovie.data.remote.TheMovieDbRemoteDataSource
import com.example.imovie.utils.Result

class TheMovieDbRepository {
    private val remoteDataSource: TheMovieDbRemoteDataSource = TheMovieDbRemoteDataSource()

    suspend fun getPopularMovies(): Result<List<Movie>, NetworkError> {
        return remoteDataSource.getPopularMovies()
    }

    suspend fun getNowPlayingMovies(): Result<List<Movie>, NetworkError> {
        return remoteDataSource.getNowPlayingMovies()
    }

    suspend fun getTopRatedMovies(): Result<List<Movie>, NetworkError> {
        return remoteDataSource.getTopRatedMovies()
    }

    suspend fun getUpcomingMovies(): Result<List<Movie>, NetworkError> {
        return remoteDataSource.getUpcomingMovies()
    }
}