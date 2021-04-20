package com.example.imovie.presentation.model

data class MovieDetailsUiModel(

    val id: String,
    val posterPath: String?,
    val title: String,
    val overview: String?,
    val release: String,
    val runtime: String
)
