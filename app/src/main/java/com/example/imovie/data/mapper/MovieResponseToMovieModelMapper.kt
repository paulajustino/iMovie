package com.example.imovie.data.mapper

import com.example.imovie.MovieModel
import com.example.imovie.data.api.MovieResponse

class MovieResponseToMovieModelMapper {
    fun mapFrom(from: MovieResponse): MovieModel {
        return from.let {
            MovieModel(
                id = "${it.id}",
                titleMovie = it.title,
                posterPath = it.posterPath,
                descriptionMovie = it.overview
            )
        }
    }
}