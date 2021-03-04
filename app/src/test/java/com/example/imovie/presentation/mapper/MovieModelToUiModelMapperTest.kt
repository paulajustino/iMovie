package com.example.imovie.presentation.mapper

import com.example.imovie.MovieModel
import com.example.imovie.MovieUiModel
import org.junit.Test
import kotlin.test.assertEquals

class MovieModelToUiModelMapperTest {

    private val mapper = MovieModelToUiModelMapper()

    @Test
    fun mapFrom_validMovieModel_shouldReturnMovieUiModel() {
        val expected = MovieUiModel(
            id = "1",
            posterPath = "PosterPathTeste"
        )

        val movieModel = MovieModel(
            id = "1",
            titleMovie = "TituloTeste",
            posterPath = "PosterPathTeste",
            descriptionMovie = "DescriptionTeste"
        )

        val actual = mapper.mapFrom(movieModel)

        assertEquals(expected = expected, actual = actual)
    }
}