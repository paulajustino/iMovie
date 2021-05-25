package com.example.imovie.domain.model

import com.example.imovie.domain.model.MovieModel

data class SectionModel (

    val id: String,
    val titleSection: String,
    val listMovies: List<MovieModel>
)