package com.example.imovie.data.mapper

import com.example.imovie.data.api.MovieDetailsResponse
import com.example.imovie.domain.model.MovieDetailsModel
import org.junit.Test
import kotlin.test.assertEquals

class MovieDetailsResponseToMovieDetailsModelMapperTest {

    private val movieDetailsMapper = MovieDetailsResponseToMovieDetailsModelDefaultMapper()

    @Test
    fun mapFrom_validMovieDetailsResponse_shouldReturnMovieDetailsModel() {
        val expected = MovieDetailsModel(
            id = "1",
            backdropPath = "backdropPathTeste",
            title = "titleTeste",
            overview = "overviewTeste",
            release = "releaseTeste",
            runtime = 139
        )

        val movieDetailsResponse = MovieDetailsResponse(
            id = 1,
            backdropPath = "backdropPathTeste",
            title = "titleTeste",
            overview = "overviewTeste",
            release = "releaseTeste",
            runtime = 139
        )

        val actual = movieDetailsMapper.mapFrom(movieDetailsResponse)

        assertEquals(
            expected = expected,
            actual = actual
        )
    }

    @Test
    fun mapFrom_movieDetailsResponseWithBackdropPathNull_shouldReturnMovieDetailsModel() {
        val expected = MovieDetailsModel(
            id = "1",
            backdropPath = null,
            title = "titleTeste",
            overview = "overviewTeste",
            release = "releaseTeste",
            runtime = 139
        )

        val movieDetailsResponse = MovieDetailsResponse(
            id = 1,
            backdropPath = null,
            title = "titleTeste",
            overview = "overviewTeste",
            release = "releaseTeste",
            runtime = 139
        )

        val actual = movieDetailsMapper.mapFrom(movieDetailsResponse)

        assertEquals(
            expected = expected,
            actual = actual
        )
    }

    @Test
    fun mapFrom_movieDetailsResponseWithOverviewNull_shouldReturnMovieDetailsModel() {
        val expected = MovieDetailsModel(
            id = "1",
            backdropPath = "backdropPathTeste",
            title = "titleTeste",
            overview = null,
            release = "releaseTeste",
            runtime = 139
        )

        val movieDetailsResponse = MovieDetailsResponse(
            id = 1,
            backdropPath = "backdropPathTeste",
            title = "titleTeste",
            overview = null,
            release = "releaseTeste",
            runtime = 139
        )

        val actual = movieDetailsMapper.mapFrom(movieDetailsResponse)

        assertEquals(
            expected = expected,
            actual = actual
        )
    }

    @Test
    fun mapFrom_movieDetailsResponseWithRuntimeNull_shouldReturnMovieDetailsModel() {
        val expected = MovieDetailsModel(
            id = "1",
            backdropPath = "backdropPathTeste",
            title = "titleTeste",
            overview = "overviewTeste",
            release = "releaseTeste",
            runtime = null
        )

        val movieDetailsResponse = MovieDetailsResponse(
            id = 1,
            backdropPath = "backdropPathTeste",
            title = "titleTeste",
            overview = "overviewTeste",
            release = "releaseTeste",
            runtime = null
        )

        val actual = movieDetailsMapper.mapFrom(movieDetailsResponse)

        assertEquals(
            expected = expected,
            actual = actual
        )
    }
}