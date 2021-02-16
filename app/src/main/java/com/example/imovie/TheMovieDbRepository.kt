package com.example.imovie

class TheMovieDbRepository {
    private val remoteDataSource: TheMovieDbRemoteDataSource = TheMovieDbRemoteDataSource()

    suspend fun getPopularMovies() : Result<List<Movie>, NetworkError> {
        return remoteDataSource.getPopularMovies()
    }

    suspend fun getLatestMovies() : Result<List<Movie>, NetworkError> {
        return remoteDataSource.getLatestMovies()
    }

    suspend fun getNowPlayingMovies() : Result<List<Movie>, NetworkError> {
        return remoteDataSource.getNowPlayingMovies()
    }

    suspend fun getTopRatedMovies() : Result<List<Movie>, NetworkError> {
        return remoteDataSource.getTopRatedMovies()
    }

    suspend fun getUpcomingMovies() : Result<List<Movie>, NetworkError> {
        return remoteDataSource.getUpcomingMovies()
    }
}