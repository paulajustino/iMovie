package com.example.imovie

class TheMovieDbRepository {
    private val remoteDataSource: TheMovieDbRemoteDataSource = TheMovieDbRemoteDataSource()

    suspend fun getPopularMovies() : Result<List<Movie>, NetworkError> {
        return remoteDataSource.getPopularMovies()
    }

//    suspend fun getSimilarMovies(movieId: String) {
//        remoteDataSource.getSimilarMovies(movieId)
//    }
//
//    suspend fun getMovieDetails(movieId: String) {
//        remoteDataSource.getMovieDetails(movieId)
//    }

}