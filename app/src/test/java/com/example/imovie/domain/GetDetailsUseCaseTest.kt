package com.example.imovie.domain

import com.example.imovie.data.repository.TheMovieDbRepository
import com.example.imovie.domain.model.MovieDetailsModel
import com.example.imovie.domain.usecase.GetDetails
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
class GetDetailsUseCaseTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val theMovieDbRepository: TheMovieDbRepository = mockk()
    private val getDetailsUseCase = GetDetails(
        theMovieDbRepository = theMovieDbRepository,
        dispatcherProvider = coroutinesTestRule.testDispatcherProvider
    )

    @Test
    fun getDetails_ResponseSuccess_shouldReturnResultWithSuccess() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val movieDetailsModel = MovieDetailsModel(
                id = "1",
                backdropPath = null,
                title = "titleTeste",
                overview = null,
                release = "2019-10-12",
                runtime = 140
            )

            prepareScenario(
                movieDetailsModel = movieDetailsModel,
                getMovieDetailsResult = Result.Success(movieDetailsModel)
            )

            val expected = Result.Success(movieDetailsModel)
            val actual = getDetailsUseCase.getDetails("1")

            assertEquals(expected = expected, actual = actual)
        }
    }

    @Test
    fun getDetails_ResponseSuccessWithNullValue_shouldReturnResultWithError() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            prepareScenario(
                movieDetailsModel = null,
                getMovieDetailsResult = Result.Success(null)
            )

            val expected = Result.Error(NetworkError())
            val actual = getDetailsUseCase.getDetails("1")

            assertEquals(expected = expected, actual = actual)
        }
    }

    @Test
    fun getDetails_ResponseError_shouldReturnResultWithError() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            prepareScenario(
                getMovieDetailsResult = Result.Error(NetworkError())
            )

            val expected = Result.Error(NetworkError())
            val actual = getDetailsUseCase.getDetails("1")

            assertEquals(expected = expected, actual = actual)
        }
    }

    private fun prepareScenario(
        movieDetailsModel: MovieDetailsModel? = MovieDetailsModel(
            id = "1",
            backdropPath = null,
            title = "titleTeste",
            overview = null,
            release = "2019-10-12",
            runtime = 140
        ),
        getMovieDetailsResult: Result<MovieDetailsModel?, NetworkError> = Result.Success(
            movieDetailsModel
        )
    ) {
        coEvery {
            theMovieDbRepository.getMovieDetails(any())
        } returns getMovieDetailsResult
    }
}