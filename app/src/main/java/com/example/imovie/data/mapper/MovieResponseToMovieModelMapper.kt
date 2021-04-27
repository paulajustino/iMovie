package com.example.imovie.data.mapper

import com.example.imovie.domain.model.MovieModel
import com.example.imovie.data.api.MovieResponse
import javax.inject.Inject

interface MovieResponseToMovieModelMapper {

    fun mapFrom(from: MovieResponse): MovieModel
}

class MovieResponseToMovieModelDefaultMapper @Inject constructor() :
    MovieResponseToMovieModelMapper {

    override fun mapFrom(from: MovieResponse): MovieModel {
        return from.let {
            MovieModel(
                id = "${it.id}",
                posterPath = it.posterPath
            )
        }
    }
}