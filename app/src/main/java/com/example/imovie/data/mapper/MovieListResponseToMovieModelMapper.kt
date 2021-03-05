package com.example.imovie.data.mapper

import com.example.imovie.MovieModel
import com.example.imovie.data.api.MovieListResponse

class MovieListResponseToMovieModelMapper constructor(
    private val movieMapper: MovieResponseToMovieModelMapper = MovieResponseToMovieModelMapper()
) {
    fun mapListFrom(from: MovieListResponse?): List<MovieModel> {

        return from?.results?.map {
            movieMapper.mapFrom(it)
        } ?: emptyList()
    }
}


