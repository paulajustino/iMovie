package com.example.imovie.data.mapper

import com.example.imovie.MovieModel
import com.example.imovie.data.api.MovieListResponse
import com.example.imovie.data.api.MovieResponse
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import org.junit.Test

class MovieListResponseToMovieModelMapperTest {

    private val movieMapper: MovieResponseToMovieModelMapper = mockk()
    private val mapper = MovieListResponseToMovieModelMapper(
        movieMapper = movieMapper
    )

    @Test
    fun mapListFrom_ValidMovieListResponse_shouldCreateMovieModelList() {

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

        val expected = mapper.mapListFrom(movieListResponse)

        assertEquals(expected[0].id, movieListResponse.results[0].id.toString())
        assertEquals(expected[0].titleMovie, movieListResponse.results[0].title)
        assertEquals(expected[0].posterPath, movieListResponse.results[0].posterPath)
        assertEquals(expected[0].descriptionMovie, movieListResponse.results[0].overview)
    }

    @Test
    fun mapListFrom_ValidMovieListPosterPathNull_shouldCreateMovieModelList() {
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

        val expected = mapper.mapListFrom(movieListResponse)

        assertEquals(expected[0].id, movieListResponse.results[0].id.toString())
        assertEquals(expected[0].titleMovie, movieListResponse.results[0].title)
        assertEquals(expected[0].posterPath, movieListResponse.results[0].posterPath)
        assertEquals(expected[0].descriptionMovie, movieListResponse.results[0].overview)
    }

    @Test
    fun mapListFrom_ValidMovieListResultsEmpty_shouldCreateMovieModelList() {
        val movieListResponse = MovieListResponse(
            results = emptyList()
        )

        val expected = mapper.mapListFrom(movieListResponse)

        assertEquals(expected, movieListResponse.results)
    }

    @Test
    fun mapListFrom_ValidMovieListNull_shouldCreateMovieModelList() {
        val movieListResponse = null

        val expected = mapper.mapListFrom(movieListResponse)

        assertEquals(expected, emptyList<MovieListResponse>())
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