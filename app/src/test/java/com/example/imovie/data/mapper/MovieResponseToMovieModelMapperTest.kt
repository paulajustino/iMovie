package com.example.imovie.data.mapper

import com.example.imovie.data.api.MovieResponse
import junit.framework.Assert
import org.junit.Test

class MovieResponseToMovieModelMapperTest {

    private val mapper = MovieResponseToMovieModelMapper()

    @Test
    fun mapFrom_ValidMovieResponse_shouldCreateMovieModel() {
        val movieResponse = MovieResponse(
            id = 1,
            title = "title",
            posterPath = "posterPath",
            overview = "overview"
        )

        val expected = mapper.mapFrom(movieResponse)

        Assert.assertEquals(expected.id, movieResponse.id.toString())
        Assert.assertEquals(expected.titleMovie, movieResponse.title)
        Assert.assertEquals(expected.posterPath, movieResponse.posterPath)
        Assert.assertEquals(expected.descriptionMovie, movieResponse.overview)
    }

    @Test
    fun mapFrom_ValidMovieResponsePosterPathNull_shouldCreateMovieModel() {
        val movieResponse = MovieResponse(
            id = 1,
            title = "title",
            posterPath = null,
            overview = "overview"
        )

        val expected = mapper.mapFrom(movieResponse)

        Assert.assertEquals(expected.id, movieResponse.id.toString())
        Assert.assertEquals(expected.titleMovie, movieResponse.title)
        Assert.assertEquals(expected.posterPath, movieResponse.posterPath)
        Assert.assertEquals(expected.descriptionMovie, movieResponse.overview)
    }
}