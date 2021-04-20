package com.example.imovie.presentation

sealed class HomeViewAction {

    object OnHomeMovieClicked : HomeViewAction()

    data class OnCarouselHomeMovieClicked(val movieId: String) : HomeViewAction()

    object OnHomeInitialized : HomeViewAction()

    object OnFavoriteMoviesClicked : HomeViewAction()
}