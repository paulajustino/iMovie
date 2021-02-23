package com.example.imovie.presentation.mapper

import com.example.imovie.MovieModel
import com.example.imovie.MovieUiModel

class MovieModelToUiModelMapper {
    fun mapFrom(from: MovieModel): MovieUiModel {
        return MovieUiModel(
            id = from.id,
            posterPath = from.posterPath
        )
    }
}