package com.example.imovie.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.imovie.domain.model.MovieDetailsModel
import com.example.imovie.domain.usecase.GetDetailsUseCase
import com.example.imovie.presentation.DetailsViewAction
import com.example.imovie.presentation.DetailsViewState
import com.example.imovie.presentation.mapper.MovieDetailsModelToUiModelMapper
import com.example.imovie.presentation.model.MovieDetailsUiModel
import com.example.imovie.utils.CoroutinesTestRule
import com.example.imovie.utils.NetworkError
import com.example.imovie.utils.Result
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class DetailsViewModelTest {

    @get:Rule
    val instantTest = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val getDetailsUseCase: GetDetailsUseCase = mockk()
    private val movieDetailsUiModelMapper: MovieDetailsModelToUiModelMapper = mockk()
    private val detailsViewState: DetailsViewState = mockk()
    private val detailsViewModel = DetailsViewModel(
        getDetailsUseCase = getDetailsUseCase,
        movieDetailsUiModelMapper = movieDetailsUiModelMapper,
        viewState = detailsViewState
    )

    @Test
    fun dispatchViewAction_OnDetailsInitializedAndGetMovieDetailsSuccess_shouldUpdateDetailsResultWithSuccess() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val movieDetailsUiModel = MovieDetailsUiModel(
                id = "1",
                backdropPath = "backdropTeste",
                title = "titleTeste",
                overview = "overviewTeste",
                release = "2019",
                runtime = "2h 20min"
            )

            val movieDetailsModel = MovieDetailsModel(
                id = "1",
                backdropPath = "backdropTeste",
                title = "titleTeste",
                overview = "overviewTeste",
                release = "2019-10-12",
                runtime = 140
            )

            val expected = DetailsResult.Success(movieDetailsUiModel)

            prepareScenario(
                movieDetailsUiModelMapperResult = movieDetailsUiModel,
                detailsResult = Result.Success(movieDetailsModel)
            )

            detailsViewModel.dispatchViewAction(DetailsViewAction.OnDetailsInitialized("1"))

            assertEquals(expected = expected, actual = detailsViewModel.detailsResult.value)
        }
    }

    @Test
    fun dispatchViewAction_OnDetailsInitializedAndGetMovieDetailsSuccess_shouldCallMovieDetailsUiModelMapper() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val movieDetailsModel = MovieDetailsModel(
                id = "1",
                backdropPath = "backdropTeste",
                title = "titleTeste",
                overview = "overviewTeste",
                release = "2019-10-12",
                runtime = 140
            )

            prepareScenario(
                detailsResult = Result.Success(movieDetailsModel)
            )

            detailsViewModel.dispatchViewAction(DetailsViewAction.OnDetailsInitialized("1"))

            verify(exactly = 1) {
                movieDetailsUiModelMapper.mapFrom(any())
            }
        }
    }

    @Test
    fun dispatchViewAction_OnDetailsInitializedAndGetMovieDetailsError_shouldUpdateDetailsResultWithError() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            prepareScenario(detailsResult = Result.Error(NetworkError()))

            detailsViewModel.dispatchViewAction(DetailsViewAction.OnDetailsInitialized("1"))

            val expected = DetailsResult.Error

            assertEquals(expected = expected, actual = detailsViewModel.detailsResult.value)
        }
    }

    private fun prepareScenario(
        movieDetailsUiModelMapperResult: MovieDetailsUiModel = MovieDetailsUiModel(
            id = "1",
            backdropPath = "backdropTeste",
            title = "titleTeste",
            overview = "overviewTeste",
            release = "2019",
            runtime = "2h 20min"
        ),
        movieDetailsModel: MovieDetailsModel = MovieDetailsModel(
            id = "1",
            backdropPath = "backdropTeste",
            title = "titleTeste",
            overview = "overviewTeste",
            release = "2019-10-12",
            runtime = 140
        ),
        detailsResult: Result<MovieDetailsModel, NetworkError> = Result.Success(movieDetailsModel)
    ) {
        coEvery {
            getDetailsUseCase.getDetails(any())
        } returns detailsResult

        coEvery {
            movieDetailsUiModelMapper.mapFrom(any())
        } returns movieDetailsUiModelMapperResult
    }
}