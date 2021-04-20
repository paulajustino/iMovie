package com.example.imovie

sealed class HomeViewAction {

    object OnHomeMovieClicked : HomeViewAction()

    data class OnCarouselHomeMovieClicked(val movieId: String) : HomeViewAction()

    object OnHomeInitialized : HomeViewAction()

    object OnFavoriteMoviesClicked : HomeViewAction()
}