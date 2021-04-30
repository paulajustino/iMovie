package com.example.imovie.data.mapper

import com.example.imovie.domain.model.MovieDetailsModel
import com.example.imovie.data.api.MovieDetailsResponse
import javax.inject.Inject

interface MovieDetailsResponseToMovieDetailsModelMapper {

    fun mapFrom(from: MovieDetailsResponse): MovieDetailsModel
}

class MovieDetailsResponseToMovieDetailsModelDefaultMapper @Inject constructor() :
    MovieDetailsResponseToMovieDetailsModelMapper {

    override fun mapFrom(from: MovieDetailsResponse): MovieDetailsModel {
        return MovieDetailsModel(
            id = "${from.id}",
            backdropPath = from.backdropPath,
            title = from.title,
            overview = from.overview,
            release = from.release,
            runtime = from.runtime
        )
    }
}