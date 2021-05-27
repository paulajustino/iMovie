package com.example.imovie.domain

import com.example.imovie.data.repository.TheMovieDbRepository
import com.example.imovie.domain.model.MovieModel
import com.example.imovie.domain.usecase.GetSimilarMovies
import com.example.imovie.utils.CoroutinesTestRule
import com.example.imovie.utils.NetworkError
import com.example.imovie.utils.Result
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class GetSimilarMoviesUseCaseTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val theMovieDbRepository: TheMovieDbRepository = mockk()
    private val getSimilarMoviesUseCase = GetSimilarMovies(
        theMovieDbRepository = theMovieDbRepository,
        dispatcherProvider = coroutinesTestRule.testDispatcherProvider
    )

    @Test
    fun getSimilarMovies_ResponseSuccessWithSimilarMoviesListNotEmpty_shouldReturnResultWithSuccess() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val similarMovieModel = MovieModel(
                id = "1",
                posterPath = "posterPathTest"
            )

            prepareScenario(
                similarMoviesResult = Result.Success(listOf(similarMovieModel))
            )

            val expected = Result.Success(listOf(similarMovieModel))
            val actual = getSimilarMoviesUseCase.getSimilarMovies("1")

            assertEquals(expected = expected, actual = actual)
        }
    }

    @Test
    fun getSimilarMovies_ResponseSuccessWithSimilarMoviesListEmpty_shouldReturnResultWithError() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            prepareScenario(
                similarMoviesResult = Result.Success(emptyList())
            )

            val expected = Result.Error(NetworkError())
            val actual = getSimilarMoviesUseCase.getSimilarMovies("1")

            assertEquals(expected = expected, actual = actual)
        }
    }

    @Test
    fun getSimilarMovies_ResponseError_shouldReturnResultWithError() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            prepareScenario(
                similarMoviesResult = Result.Error(NetworkError())
            )

            val expected = Result.Error(NetworkError())
            val actual = getSimilarMoviesUseCase.getSimilarMovies("1")

            assertEquals(expected = expected, actual = actual)
        }
    }

    private fun prepareScenario(
        movieModel: MovieModel = MovieModel(
            id = "1",
            posterPath = "posterTest"
        ),
        similarMoviesResult: Result<List<MovieModel>, NetworkError> = Result.Success(
            listOf(
                movieModel
            )
        )
    ) {
        coEvery {
            theMovieDbRepository.getSimilarMovies(any())
        } returns similarMoviesResult
    }
}