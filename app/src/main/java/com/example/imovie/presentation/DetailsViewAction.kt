package com.example.imovie.presentation

sealed class DetailsViewAction {

    data class OnDetailsInitialized(val movieId: String): DetailsViewAction()

    data class OnSimilarMovieClicked(val movieId: String): DetailsViewAction()
}