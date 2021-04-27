package com.example.imovie.presentation.mapper

import com.example.imovie.domain.model.MovieDetailsModel
import com.example.imovie.presentation.model.MovieDetailsUiModel
import org.junit.Test
import kotlin.test.assertEquals

class MovieDetailsModelToUiModelMapperTest {

    private val mapper = MovieDetailsModelToUiModelDefaultMapper()

    @Test
    fun mapFrom_validMovieDetailsModel_shouldReturnMovieDetailsUiModel() {
        val expected = MovieDetailsUiModel(
            id = "1",
            backdropPath = "posterPathTeste",
            title = "titleTeste",
            overview = "overviewTeste",
            release = "2018",
            runtime = "2h 19min"
        )

        val movieDetailsModel = MovieDetailsModel(
            id = "1",
            backdropPath = "posterPathTeste",
            title = "titleTeste",
            overview = "overviewTeste",
            release = "2018-10-12",
            runtime = 139
        )

        val actual = mapper.mapFrom(movieDetailsModel)

        assertEquals(expected = expected, actual = actual)
    }
}