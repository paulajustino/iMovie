package com.example.imovie.presentation.mapper

import com.example.imovie.domain.model.MovieModel
import com.example.imovie.presentation.model.MovieUiModel
import javax.inject.Inject

interface MovieListModelToUiModelMapper {

    fun mapListFrom(from: List<MovieModel>): List<MovieUiModel>
}

class MovieListModelToUiModelDefaultMapper @Inject constructor(
    private val movieModelToUiModelMapper: MovieModelToUiModelMapper
) : MovieListModelToUiModelMapper {
    override fun mapListFrom(from: List<MovieModel>): List<MovieUiModel> {

        return from.map {
            movieModelToUiModelMapper.mapFrom(it)
        }
    }
}