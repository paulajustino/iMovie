package com.example.imovie.data.mapper

import com.example.imovie.Movie
import com.example.imovie.data.api.MovieResponse

class MovieResponseToMovieModelMapper {
    fun mapFrom(from: MovieResponse): Movie {
        return from.let {
            Movie(
                    id = "${it.id}",
                    titleMovie = it.title,
                    posterPath = it.posterPath,
                    descriptionMovie = it.overview
            )
        }
    }
}