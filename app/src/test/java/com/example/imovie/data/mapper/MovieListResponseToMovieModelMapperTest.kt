package com.example.imovie.data.mapper

import com.example.imovie.MovieModel
import com.example.imovie.data.api.MovieListResponse
import com.example.imovie.data.api.MovieResponse
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import kotlin.test.assertEquals

class MovieListResponseToMovieModelMapperTest {

    private val movieMapper: MovieResponseToMovieModelMapper = mockk()
    private val mapper = MovieListResponseToMovieModelMapper(
        movieMapper = movieMapper
    )

    @Test
    fun mapListFrom_validMovieListResponse_shouldReturnMovieModelList() {

        val movieMapperResult = MovieModel(
            id = "1",
            titleMovie = "title",
            posterPath = "posterPath",
            descriptionMovie = "overview"
        )

        prepareScenario(movieMapperResult)

        val movieListResponse = MovieListResponse(
            results = listOf(
                MovieResponse(
                    id = 1,
                    title = "title",
                    posterPath = "posterPath",
                    overview = "overview"
                )
            )
        )

        val actual = mapper.mapListFrom(movieListResponse)

        assertEquals(
            expected = movieMapperResult,
            actual = actual.first()
        )
    }

    @Test
    fun mapListFrom_movieListResponseWithPosterPathNull_shouldReturnMovieModelList() {
        val movieMapperResult = MovieModel(
            id = "1",
            titleMovie = "title",
            posterPath = null,
            descriptionMovie = "overview"
        )

        prepareScenario(movieMapperResult)

        val movieListResponse = MovieListResponse(
            results = listOf(
                MovieResponse(
                    id = 1,
                    title = "title",
                    posterPath = null,
                    overview = "overview"
                )
            )
        )

        val actual = mapper.mapListFrom(movieListResponse)

        assertEquals(
            expected = movieMapperResult,
            actual = actual.first()
        )
    }

    @Test
    fun mapListFrom_movieListResultsEmpty_shouldReturnEmptyList() {
        val movieListResponse = MovieListResponse(
            results = emptyList()
        )

        val actual = mapper.mapListFrom(movieListResponse)

        assertEquals(
            expected = emptyList(),
            actual = actual
        )
    }

    @Test
    fun mapListFrom_movieListResponseNull_shouldReturnEmptyList() {
        val movieListResponse = null

        val actual = mapper.mapListFrom(movieListResponse)

        assertEquals(
            expected = emptyList(),
            actual = actual
        )
    }

    private fun prepareScenario(
        movieMapperResult: MovieModel = MovieModel(
            id = "1",
            titleMovie = "titleTeste",
            posterPath = "posterTeste",
            descriptionMovie = "descriptionTeste"
        )
    ) {
        every {
            movieMapper.mapFrom(any())
        } returns movieMapperResult
    }
}