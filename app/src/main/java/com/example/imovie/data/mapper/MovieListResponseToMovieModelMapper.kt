package com.example.imovie.data.mapper

import com.example.imovie.Movie
import com.example.imovie.data.api.MovieListResponse

class MovieListResponseToMovieModelMapper() {
    private val movieMapper = MovieResponseToMovieModelMapper()

    fun mapListFrom(from: MovieListResponse?): List<Movie> {
        val movieListResponse = from?.results.orEmpty()

        return movieListResponse.map {
            movieMapper.mapFrom(it)
        }
    }
}
