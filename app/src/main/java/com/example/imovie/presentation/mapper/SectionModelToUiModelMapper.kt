package com.example.imovie.presentation.mapper

import com.example.imovie.SectionModel
import com.example.imovie.SectionUiModel

class SectionModelToUiModelMapper {
    fun mapFrom(from: List<SectionModel>): List<SectionUiModel> {
        return from.map {
            SectionUiModel(
                id = it.id,
                titleSection = it.titleSection,
                listMovies = it.listMovies
            )
        }
    }
}