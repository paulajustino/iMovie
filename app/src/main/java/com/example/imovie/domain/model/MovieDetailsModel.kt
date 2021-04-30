package com.example.imovie.domain.model

data class MovieDetailsModel(

    val id: String,
    val backdropPath: String?,
    val title: String,
    val overview: String?,
    val release: String,
    val runtime: Int?
)