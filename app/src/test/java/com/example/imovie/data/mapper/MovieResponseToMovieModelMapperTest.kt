package com.example.imovie.data.mapper

import com.example.imovie.MovieModel
import com.example.imovie.data.api.MovieResponse
import org.junit.Test
import kotlin.test.assertEquals

class MovieResponseToMovieModelMapperTest {

    private val mapper = MovieResponseToMovieModelMapper()

    @Test
    fun mapFrom_validMovieResponse_shouldReturnMovieModel() {
        val expected = MovieModel(
            id = "1",
            titleMovie = "title",
            posterPath = "posterPath",
            descriptionMovie = "overview"
        )

        val movieResponse = MovieResponse(
            id = 1,
            title = "title",
            posterPath = "posterPath",
            overview = "overview"
        )

        val actual = mapper.mapFrom(movieResponse)

        assertEquals(
            expected = expected,
            actual = actual
        )
    }

    @Test
    fun mapFrom_movieResponseWithPosterPathNull_shouldReturnMovieModel() {
        val expected = MovieModel(
            id = "1",
            titleMovie = "title",
            posterPath = null,
            descriptionMovie = "overview"
        )

        val movieResponse = MovieResponse(
            id = 1,
            title = "title",
            posterPath = null,
            overview = "overview"
        )

        val actual = mapper.mapFrom(movieResponse)

        assertEquals(
            expected = expected,
            actual = actual
        )
    }
}