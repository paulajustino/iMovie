package com.example.imovie.data.mapper

import com.example.imovie.MovieModel
import com.example.imovie.data.api.MovieListResponse

class MovieListResponseToMovieModelMapper() {
    private val movieMapper = MovieResponseToMovieModelMapper()

    fun mapListFrom(from: MovieListResponse?): List<MovieModel> {
        val movieListResponse = from?.results.orEmpty()

        return movieListResponse.map {
            movieMapper.mapFrom(it)
        }
    }
}
