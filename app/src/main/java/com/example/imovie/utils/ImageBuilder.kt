package com.example.imovie.utils

object ImageBuilder {
    private val posterBaseUrl = "https://image.tmdb.org/t/p/"

    fun build(posterPath : String, imagePresets: ImagePresets) : String {
        val resolution = ImageResolution.getResolution(width = imagePresets.width.dp).rawValue
        return posterBaseUrl + resolution + posterPath
    }
}


