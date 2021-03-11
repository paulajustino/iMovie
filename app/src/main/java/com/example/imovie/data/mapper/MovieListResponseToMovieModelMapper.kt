package com.example.imovie.data.mapper

import com.example.imovie.MovieModel
import com.example.imovie.data.api.MovieListResponse
import javax.inject.Inject

interface MovieListResponseToMovieModelMapper {

    fun mapListFrom(from: MovieListResponse?): List<MovieModel>
}

class MovieListResponseToMovieModelDefaultMapper @Inject constructor(
    private val movieMapper: MovieResponseToMovieModelMapper
) : MovieListResponseToMovieModelMapper {
    override fun mapListFrom(from: MovieListResponse?): List<MovieModel> {

        return from?.results?.map {
            movieMapper.mapFrom(it)
        } ?: emptyList()
    }
}


