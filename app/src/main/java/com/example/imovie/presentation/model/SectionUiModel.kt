package com.example.imovie.presentation.model

import com.example.imovie.presentation.model.MovieUiModel

data class SectionUiModel (

    val id: String,
    val titleSection: String,
    val listMovies: List<MovieUiModel>
)
