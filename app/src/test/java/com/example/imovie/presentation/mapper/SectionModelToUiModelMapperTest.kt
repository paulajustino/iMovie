package com.example.imovie.presentation.mapper

import com.example.imovie.MovieModel
import com.example.imovie.MovieUiModel
import com.example.imovie.SectionModel
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert
import org.junit.Test

class SectionModelToUiModelMapperTest {

    private val movieMapper: MovieModelToUiModelMapper = mockk()
    private val mapper = SectionModelToUiModelMapper(
        movieUiModelMapper = movieMapper
    )

    @Test
    fun mapFrom_validSectionModelListWithMovieListEmpty_shouldCreateSectionUiModelList() {
        val sectionModelList = listOf(
            SectionModel(
                id = "1",
                titleSection = "TitleSection",
                listMovies = emptyList()
            )
        )

        val expected = mapper.mapFrom(sectionModelList)

        Assert.assertEquals(expected[0].id, sectionModelList[0].id)
        Assert.assertEquals(expected[0].titleSection, sectionModelList[0].titleSection)
        Assert.assertEquals(expected[0].listMovies, sectionModelList[0].listMovies)
    }

    @Test
    fun mapFrom_validSectionModelListWithMovieListNotEmpty_shouldCreateSectionUiModelList() {
        prepareScenario(
            movieMapperResult = MovieUiModel(
                id = "1",
                posterPath = "PosterPath"
            )
        )

        val movieModelList = listOf(
            MovieModel(
                id = "1",
                titleMovie = "TitleMovie",
                posterPath = "PosterPath",
                descriptionMovie = "DescriptionMovie"
            )
        )

        val sectionModelList = listOf(
            SectionModel(
                id = "1",
                titleSection = "TitleSection",
                listMovies = movieModelList
            )
        )

        val expected = mapper.mapFrom(sectionModelList)

        Assert.assertEquals(expected[0].id, sectionModelList[0].id)
        Assert.assertEquals(expected[0].titleSection, sectionModelList[0].titleSection)
        Assert.assertEquals(expected[0].listMovies[0].id, sectionModelList[0].listMovies[0].id)
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