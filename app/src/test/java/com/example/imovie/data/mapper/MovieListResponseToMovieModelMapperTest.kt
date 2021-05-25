package com.example.imovie.data.mapper

import com.example.imovie.domain.model.MovieModel
import com.example.imovie.data.api.MovieListResponse
import com.example.imovie.data.api.MovieResponse
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import kotlin.test.assertEquals

class MovieListResponseToMovieModelMapperTest {

    private val movieMapper: MovieResponseToMovieModelMapper = mockk()
    private val mapper = MovieListResponseToMovieModelDefaultMapper(
        movieMapper = movieMapper
    )

    @Test
    fun mapListFrom_validMovieListResponse_shouldReturnMovieModelList() {

        val movieMapperResult = MovieModel(
            id = "1",
            posterPath = "posterPath"
        )

        prepareScenario(movieMapperResult)

        val movieListResponse = MovieListResponse(
            results = listOf(
                MovieResponse(
                    id = 1,
                    posterPath = "posterPath"
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
            posterPath = null
        )

        prepareScenario(movieMapperResult)

        val movieListResponse = MovieListResponse(
            results = listOf(
                MovieResponse(
                    id = 1,
                    posterPath = null
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
            posterPath = "posterTeste"
        )
    ) {
        every {
            movieMapper.mapFrom(any())
        } returns movieMapperResult
    }
}