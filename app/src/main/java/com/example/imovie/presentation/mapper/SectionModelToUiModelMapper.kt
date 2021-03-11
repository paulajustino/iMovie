package com.example.imovie.presentation.mapper

import com.example.imovie.SectionModel
import com.example.imovie.SectionUiModel
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