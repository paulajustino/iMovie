package com.example.imovie.service

import com.squareup.moshi.Json

data class Response(

     @field:Json(name="results")
     val results: List<MovieResponse>
)

data class MovieResponse(

     @field:Json(name="id")
     val id: Long,

     @field:Json(name="title")
     val title: String,

     @field:Json(name="overview")
     val overview: String,

     @field:Json(name="poster_path")
     val posterPath: String
)
