package com.example.imovie.data.mapper

import com.example.imovie.domain.model.MovieModel
import com.example.imovie.data.api.MovieResponse
import org.junit.Test
import kotlin.test.assertEquals

class MovieResponseToMovieModelMapperTest {

    private val mapper = MovieResponseToMovieModelDefaultMapper()

    @Test
    fun mapFrom_validMovieResponse_shouldReturnMovieModel() {
        val expected = MovieModel(
            id = "1",
            posterPath = "posterPath"
        )

        val movieResponse = MovieResponse(
            id = 1,
            posterPath = "posterPath"
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
            posterPath = null
        )

        val movieResponse = MovieResponse(
            id = 1,
            posterPath = null
        )

        val actual = mapper.mapFrom(movieResponse)

        assertEquals(
            expected = expected,
            actual = actual
        )
    }
}