package com.example.imovie.presentation.mapper

import com.example.imovie.MovieModel
import junit.framework.Assert.assertEquals
import org.junit.Test

class MovieModelToUiModelMapperTest {

    private val mapper = MovieModelToUiModelMapper()

    @Test
    fun mapFrom_validMovieModel_shouldCreateMovieUiModel() {
        val movieModel = MovieModel(
            id = "1",
            titleMovie = "TituloTeste",
            posterPath = "PosterPathTeste",
            descriptionMovie = "DescriptionTeste"
        )

        val expected = mapper.mapFrom(movieModel)

        assertEquals(expected.id, movieModel.id)
        assertEquals(expected.posterPath, movieModel.posterPath)
    }
}