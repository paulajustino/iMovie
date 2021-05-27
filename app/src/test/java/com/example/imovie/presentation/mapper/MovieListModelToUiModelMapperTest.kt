package com.example.imovie.presentation.mapper

import com.example.imovie.domain.model.MovieModel
import com.example.imovie.presentation.model.MovieUiModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import kotlin.test.assertEquals

class MovieListModelToUiModelMapperTest {

    private val movieModelToUiModelMapper: MovieModelToUiModelMapper = mockk()
    private val mapper = MovieListModelToUiModelDefaultMapper(
        movieModelToUiModelMapper = movieModelToUiModelMapper
    )

    @Test
    fun mapListFrom_validMovieModelList_shouldReturnMovieUiModelList() {
        val movieUiModel = MovieUiModel(
            id = "1",
            posterPath = "posterPathTest"
        )

        val expected = listOf(movieUiModel)

        val movieModel = MovieModel(
            id = "1",
            posterPath = "posterPathTest"
        )

        prepareScenario(
            movieModelToUiModelMapperResult = movieUiModel
        )

        val actual = mapper.mapListFrom(
            listOf(
                movieModel
            )
        )

        assertEquals(expected = expected, actual = actual)
    }

    private fun prepareScenario(
        movieModelToUiModelMapperResult: MovieUiModel = MovieUiModel(
            id = "1",
            posterPath = "posterPathTest"
        )
    ) {
        every {
            movieModelToUiModelMapper.mapFrom(any())
        } returns movieModelToUiModelMapperResult
    }
}