package com.example.imovie.presentation.mapper

import com.example.imovie.SectionModel
import com.example.imovie.SectionUiModel

class SectionModelToUiModelMapper {
    private val movieUiModelMapper = MovieModelToUiModelMapper()

    fun mapFrom(from: List<SectionModel>): List<SectionUiModel> {
        return from.map { sectionModel ->
            SectionUiModel(
                id = sectionModel.id,
                titleSection = sectionModel.titleSection,
                listMovies = sectionModel.listMovies.map {
                    movieUiModelMapper.mapFrom(it)
                }
            )
        }
    }
}