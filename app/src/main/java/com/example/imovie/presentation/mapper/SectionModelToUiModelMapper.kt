package com.example.imovie.presentation.mapper

import com.example.imovie.domain.model.SectionModel
import com.example.imovie.presentation.model.SectionUiModel
import javax.inject.Inject

interface SectionModelToUiModelMapper {

    fun mapFrom(from: List<SectionModel>): List<SectionUiModel>
}

class SectionModelToUiModelDefaultMapper @Inject constructor(
    private val movieUiModelMapper: MovieModelToUiModelMapper
) : SectionModelToUiModelMapper {
    override fun mapFrom(from: List<SectionModel>): List<SectionUiModel> {
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