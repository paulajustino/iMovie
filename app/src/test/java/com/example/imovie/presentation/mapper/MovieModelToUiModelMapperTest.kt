package com.example.imovie.presentation.mapper

import com.example.imovie.domain.model.MovieModel
import com.example.imovie.presentation.model.MovieUiModel
import org.junit.Test
import kotlin.test.assertEquals

class MovieModelToUiModelMapperTest {

    private val mapper = MovieModelToUiModelDefaultMapper()

    @Test
    fun mapFrom_validMovieModel_shouldReturnMovieUiModel() {
        val expected = MovieUiModel(
            id = "1",
            posterPath = "PosterPathTeste"
        )

        val movieModel = MovieModel(
            id = "1",
            posterPath = "PosterPathTeste"
        )

        val actual = mapper.mapFrom(movieModel)

        assertEquals(expected = expected, actual = actual)
    }
}