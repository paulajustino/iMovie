package com.example.imovie.presentation.mapper

import com.example.imovie.domain.model.MovieModel
import com.example.imovie.presentation.model.MovieUiModel
import javax.inject.Inject

interface MovieModelToUiModelMapper {

    fun mapFrom(from: MovieModel): MovieUiModel
}

class MovieModelToUiModelDefaultMapper @Inject constructor() : MovieModelToUiModelMapper {
    override fun mapFrom(from: MovieModel): MovieUiModel {
        return MovieUiModel(
            id = from.id,
            posterPath = from.posterPath
        )
    }
}