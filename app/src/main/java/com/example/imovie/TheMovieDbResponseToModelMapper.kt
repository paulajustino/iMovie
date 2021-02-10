package com.example.imovie

class TheMovieDbResponseToModelMapper() {

    fun mapFrom(from: TheMovieDbResponse?): List<Movie> {
        val movieListResponse = from?.results.orEmpty()

        return movieListResponse.map {
            Movie(
                    id = "${it.id}",
                    titleMovie = it.title,
                    imageUrl = "https://image.tmdb.org/t/p/w92" + it.posterPath,
                    descriptionMovie = it.overview
            )
        }
    }
}
