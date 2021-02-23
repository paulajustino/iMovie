package com.example.imovie.data.repository

import com.example.imovie.MovieModel
import com.example.imovie.NetworkError
import com.example.imovie.data.remote.TheMovieDbRemoteDataSource
import com.example.imovie.utils.Result

class TheMovieDbRepository {
    private val remoteDataSource: TheMovieDbRemoteDataSource = TheMovieDbRemoteDataSource()

    suspend fun getPopularMovies(): Result<List<MovieModel>, NetworkError> {
        return remoteDataSource.getPopularMovies()
    }

    suspend fun getNowPlayingMovies(): Result<List<MovieModel>, NetworkError> {
        return remoteDataSource.getNowPlayingMovies()
    }

    suspend fun getTopRatedMovies(): Result<List<MovieModel>, NetworkError> {
        return remoteDataSource.getTopRatedMovies()
    }

    suspend fun getUpcomingMovies(): Result<List<MovieModel>, NetworkError> {
        return remoteDataSource.getUpcomingMovies()
    }
}