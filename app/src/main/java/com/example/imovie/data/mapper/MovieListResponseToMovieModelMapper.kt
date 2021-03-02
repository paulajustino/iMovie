package com.example.imovie.data.mapper

import com.example.imovie.MovieModel
import com.example.imovie.data.api.MovieListResponse

class MovieListResponseToMovieModelMapper constructor(
    private val movieMapper: MovieResponseToMovieModelMapper = MovieResponseToMovieModelMapper()
) {
    fun mapListFrom(from: MovieListResponse?): List<MovieModel> {
        val movieListResponse = from?.results ?: emptyList()

        return if (movieListResponse.isNotEmpty()) {
            movieListResponse.map {
                movieMapper.mapFrom(it)
            }
        } else {
            emptyList()
        }
    }
}
