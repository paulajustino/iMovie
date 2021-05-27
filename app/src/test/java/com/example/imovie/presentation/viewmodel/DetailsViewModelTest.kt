package com.example.imovie.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.imovie.domain.model.MovieDetailsModel
import com.example.imovie.domain.model.MovieModel
import com.example.imovie.domain.usecase.GetDetailsUseCase
import com.example.imovie.domain.usecase.GetSimilarMoviesUseCase
import com.example.imovie.presentation.DetailsViewAction
import com.example.imovie.presentation.DetailsViewState
import com.example.imovie.presentation.mapper.MovieDetailsModelToUiModelMapper
import com.example.imovie.presentation.mapper.MovieListModelToUiModelMapper
import com.example.imovie.presentation.model.MovieDetailsUiModel
import com.example.imovie.presentation.model.MovieUiModel
import com.example.imovie.utils.CoroutinesTestRule
import com.example.imovie.utils.NetworkError
import com.example.imovie.utils.Result
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class DetailsViewModelTest {

    @get:Rule
    val instantTest = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val getDetailsUseCase: GetDetailsUseCase = mockk()
    private val movieDetailsUiModelMapper: MovieDetailsModelToUiModelMapper = mockk()
    private val detailsViewState: DetailsViewState = spyk()
    private val movieListUiModelMapper: MovieListModelToUiModelMapper = mockk()
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase = mockk()
    private val detailsViewModel = DetailsViewModel(
        getDetailsUseCase = getDetailsUseCase,
        movieDetailsUiModelMapper = movieDetailsUiModelMapper,
        viewState = detailsViewState,
        getSimilarUseCase = getSimilarMoviesUseCase,
        movieListUiModelMapper = movieListUiModelMapper
    )

    @Test
    fun dispatchViewAction_OnDetailsInitializedAndGetMovieDetailsSuccess_shouldUpdateDetailsResultWithSuccess() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val movieDetailsUiModel = MovieDetailsUiModel(
                id = "1",
                backdropPath = "backdropTest",
                title = "titleTest",
                overview = "overviewTest",
                release = "2019",
                runtime = "2h 20min"
            )

            val movieDetailsModel = MovieDetailsModel(
                id = "1",
                backdropPath = "backdropTest",
                title = "titleTest",
                overview = "overviewTest",
                release = "2019-10-12",
                runtime = 140
            )

            val expected = DetailsResult.Success(movieDetailsUiModel)

            prepareScenario(
                movieDetailsUiModelMapperResult = movieDetailsUiModel,
                detailsResult = Result.Success(movieDetailsModel)
            )

            detailsViewModel.dispatchViewAction(DetailsViewAction.OnDetailsInitialized("1"))

            assertEquals(
                expected = expected,
                actual = detailsViewModel.viewState.detailsResult.value
            )
        }
    }

    @Test
    fun dispatchViewAction_OnDetailsInitializedAndGetSimilarMoviesSuccess_shouldUpdateSimilarResultWithSuccess() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val movieModel = MovieModel(
                id = "1",
                posterPath = "PosterPathTest"
            )

            val movieUiModel = MovieUiModel(
                id = "1",
                posterPath = "PosterPathTest"
            )

            prepareScenario(
                movieListUiModelMapperResult = listOf(movieUiModel),
                similarResult = Result.Success(listOf(movieModel))
            )

            val expected = SimilarResult.Success(listOf(movieUiModel))

            detailsViewModel.dispatchViewAction(DetailsViewAction.OnDetailsInitialized("1"))

            assertEquals(
                expected = expected,
                actual = detailsViewModel.viewState.similarResult.value
            )
        }
    }

    @Test
    fun dispatchViewAction_OnDetailsInitializedAndGetMovieDetailsSuccess_shouldCallMovieDetailsUiModelMapper() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val movieDetailsModel = MovieDetailsModel(
                id = "1",
                backdropPath = "backdropTest",
                title = "titleTest",
                overview = "overviewTest",
                release = "2019-10-12",
                runtime = 140
            )

            prepareScenario(
                detailsResult = Result.Success(movieDetailsModel)
            )

            detailsViewModel.dispatchViewAction(DetailsViewAction.OnDetailsInitialized("1"))

            verify(exactly = 1) {
                movieDetailsUiModelMapper.mapFrom(movieDetailsModel)
            }
        }
    }

    @Test
    fun dispatchViewAction_OnDetailsInitializedAndGetSimilarMoviesSuccess_shouldCallMovieListUiModelMapper() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val movieModel = MovieModel(
                id = "1",
                posterPath = "PosterPathTest"
            )

            prepareScenario(
                similarResult = Result.Success(listOf(movieModel))
            )

            detailsViewModel.dispatchViewAction(DetailsViewAction.OnDetailsInitialized("1"))

            verify(exactly = 1) {
                movieListUiModelMapper.mapListFrom(listOf(movieModel))
            }
        }
    }

    @Test
    fun dispatchViewAction_OnDetailsInitializedAndGetMovieDetailsError_shouldUpdateDetailsResultWithError() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            prepareScenario(detailsResult = Result.Error(NetworkError()))

            detailsViewModel.dispatchViewAction(DetailsViewAction.OnDetailsInitialized("1"))

            val expected = DetailsResult.Error

            assertEquals(
                expected = expected,
                actual = detailsViewModel.viewState.detailsResult.value
            )
        }
    }

    @Test
    fun dispatchViewAction_OnDetailsInitializedAndGetSimilarMoviesError_shouldUpdateSimilarResultWithError() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            prepareScenario(similarResult = Result.Error(NetworkError()))

            detailsViewModel.dispatchViewAction(DetailsViewAction.OnDetailsInitialized("1"))

            val expected = SimilarResult.Error

            assertEquals(
                expected = expected,
                actual = detailsViewModel.viewState.similarResult.value
            )
        }
    }

    @Test
    fun dispatchViewAction_OnSimilarMovieClickedAndMovieIdIsNotEmpty_shouldUpdateViewStateWithOpenSimilarMovieDetails() {
        detailsViewModel.dispatchViewAction(DetailsViewAction.OnSimilarMovieClicked("1"))

        assertTrue(detailsViewModel.viewState.action.value is DetailsViewState.Action.OpenSimilarMovieDetails)
    }

    private fun prepareScenario(
        movieDetailsUiModelMapperResult: MovieDetailsUiModel = MovieDetailsUiModel(
            id = "1",
            backdropPath = "backdropTest",
            title = "titleTest",
            overview = "overviewTest",
            release = "2019",
            runtime = "2h 20min"
        ),
        movieDetailsModel: MovieDetailsModel = MovieDetailsModel(
            id = "1",
            backdropPath = "backdropTest",
            title = "titleTest",
            overview = "overviewTest",
            release = "2019-10-12",
            runtime = 140
        ),
        detailsResult: Result<MovieDetailsModel, NetworkError> = Result.Success(movieDetailsModel),
        movieModel: MovieModel = MovieModel(
            id = "1", posterPath = "PosterPathTest"
        ),
        movieUiModel: MovieUiModel = MovieUiModel(
            id = "1",
            posterPath = "PosterPathTest"
        ),
        movieListUiModelMapperResult: List<MovieUiModel> = listOf(movieUiModel),
        similarResult: Result<List<MovieModel>, NetworkError> = Result.Success(listOf(movieModel))
    ) {
        coEvery {
            getDetailsUseCase.getDetails(any())
        } returns detailsResult

        coEvery {
            movieDetailsUiModelMapper.mapFrom(any())
        } returns movieDetailsUiModelMapperResult

        coEvery {
            getSimilarMoviesUseCase.getSimilarMovies(any())
        } returns similarResult

        coEvery {
            movieListUiModelMapper.mapListFrom(any())
        } returns movieListUiModelMapperResult
    }
}