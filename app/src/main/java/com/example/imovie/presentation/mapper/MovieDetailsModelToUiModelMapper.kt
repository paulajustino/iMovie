package com.example.imovie.presentation.mapper

import com.example.imovie.domain.model.MovieDetailsModel
import com.example.imovie.presentation.model.MovieDetailsUiModel
import javax.inject.Inject

interface MovieDetailsModelToUiModelMapper {

    fun mapFrom(from: MovieDetailsModel): MovieDetailsUiModel
}

class MovieDetailsModelToUiModelDefaultMapper @Inject constructor() :
    MovieDetailsModelToUiModelMapper {
    override fun mapFrom(from: MovieDetailsModel): MovieDetailsUiModel {
        return MovieDetailsUiModel(
            id = from.id,
            posterPath = from.posterPath,
            title = from.title,
            overview = from.overview,
            release = convertRelease(from.release),
            runtime = convertRuntime(from.runtime)
        )
    }

    private fun convertRuntime(runtime: Int?): String {
        var movieRuntime = runtime
        var hours = 0
        var minutes: Int

        return if (movieRuntime != null) {
            while (movieRuntime >= 60) {
                movieRuntime -= 60
                hours += 1
            }
            minutes = movieRuntime

            hours.toString() + "h " + minutes.toString() + "min"
        } else {
            "0h 0min"
        }
    }

    private fun convertRelease(release: String): String {
        return release.substring(0, 4)
    }
}

