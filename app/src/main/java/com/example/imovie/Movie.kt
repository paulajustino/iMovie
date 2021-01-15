package com.example.imovie

import com.squareup.moshi.Json

data class Movie (

    @field:Json(name="id")
    val id: String,
    @field:Json(name="poster_path")
    val imageUrl: String,
    @field:Json(name="title")
    val titleMovie: String,
    @field:Json(name="overview")
    val descriptionMovie: String
)