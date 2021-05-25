package com.example.imovie.data.api

import com.squareup.moshi.Json

data class MovieListResponse(

        @field:Json(name="results")
        val results: List<MovieResponse>
)

data class MovieResponse(

        @field:Json(name="id")
        val id: Long,

        @field:Json(name="poster_path")
        val posterPath: String?
)

data class MovieDetailsResponse(

        @field:Json(name="id")
        val id: Long,

        @field:Json(name="backdrop_path")
        val backdropPath: String?,

        @field:Json(name="title")
        val title: String,

        @field:Json(name="overview")
        val overview: String?,

        @field:Json(name="release_date")
        val release: String,

        @field:Json(name="runtime")
        val runtime: Int?
)
