package com.example.imovie

class TheMovieDbUseCase {
    private val theMovieDbRepository: TheMovieDbRepository = TheMovieDbRepository()

    suspend fun getPopularMovies() : Result<List<Movie>, NetworkError> {
        return theMovieDbRepository.getPopularMovies()
    }
}