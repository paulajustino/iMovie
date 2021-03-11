package com.example.imovie.data.repository

import com.example.imovie.MovieModel
import com.example.imovie.NetworkError
import com.example.imovie.data.remote.TheMovieDbRemoteDataSource
import com.example.imovie.utils.Result
import javax.inject.Inject

interface TheMovieDbRepository {

    suspend fun getPopularMovies(): Result<List<MovieModel>, NetworkError>

    suspend fun getNowPlayingMovies(): Result<List<MovieModel>, NetworkError>

    suspend fun getTopRatedMovies(): Result<List<MovieModel>, NetworkError>

    suspend fun getUpcomingMovies(): Result<List<MovieModel>, NetworkError>
}

class TheMovieDbDefaultRepository @Inject constructor(
    private val remoteDataSource: TheMovieDbRemoteDataSource
) : TheMovieDbRepository {

    override suspend fun getPopularMovies(): Result<List<MovieModel>, NetworkError> {
        return remoteDataSource.getPopularMovies()
    }

    override suspend fun getNowPlayingMovies(): Result<List<MovieModel>, NetworkError> {
        return remoteDataSource.getNowPlayingMovies()
    }

    override suspend fun getTopRatedMovies(): Result<List<MovieModel>, NetworkError> {
        return remoteDataSource.getTopRatedMovies()
    }

    override suspend fun getUpcomingMovies(): Result<List<MovieModel>, NetworkError> {
        return remoteDataSource.getUpcomingMovies()
    }
}