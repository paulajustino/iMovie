package com.example.imovie.presentation.mapper

import com.example.imovie.domain.model.MovieModel
import com.example.imovie.presentation.model.MovieUiModel
import com.example.imovie.domain.model.SectionModel
import com.example.imovie.presentation.model.SectionUiModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import kotlin.test.assertEquals

class SectionModelToUiModelMapperTest {

    private val movieMapper: MovieModelToUiModelMapper = mockk()
    private val mapper = SectionModelToUiModelDefaultMapper(
        movieUiModelMapper = movieMapper
    )

    @Test
    fun mapFrom_validSectionModelListWithMovieListEmpty_shouldReturnSectionUiModelList() {
        val expected = listOf(
            SectionUiModel(
                id = "1",
                titleSection = "TitleSection",
                listMovies = emptyList()
            )
        )

        val sectionModelList = listOf(
            SectionModel(
                id = "1",
                titleSection = "TitleSection",
                listMovies = emptyList()
            )
        )

        val actual = mapper.mapFrom(sectionModelList)

        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun mapFrom_validSectionModelListWithMovieListNotEmpty_shouldReturnSectionUiModelList() {
        val movieUiModelList = listOf(
            MovieUiModel(
                id = "1",
                posterPath = "PosterPath"
            )
        )

        val expected = listOf(
            SectionUiModel(
                id = "1",
                titleSection = "TitleSection",
                listMovies = movieUiModelList
            )
        )

        prepareScenario(
            movieMapperResult = MovieUiModel(
                id = "1",
                posterPath = "PosterPath"
            )
        )

        val movieModelList = listOf(
            MovieModel(
                id = "1",
                posterPath = "PosterPath"
            )
        )

        val sectionModelList = listOf(
            SectionModel(
                id = "1",
                titleSection = "TitleSection",
                listMovies = movieModelList
            )
        )

        val actual = mapper.mapFrom(sectionModelList)

        assertEquals(expected = expected, actual = actual)
    }

    private fun prepareScenario(
        movieMapperResult: MovieUiModel = MovieUiModel(
            id = "1",
            posterPath = "Teste"
        )
    ) {
        every {
            movieMapper.mapFrom(any())
        } returns movieMapperResult
    }
}