package com.example.imovie.presentation.model

data class MovieDetailsUiModel(

    val id: String,
    val backdropPath: String?,
    val title: String,
    val overview: String?,
    val release: String,
    val runtime: String
)
