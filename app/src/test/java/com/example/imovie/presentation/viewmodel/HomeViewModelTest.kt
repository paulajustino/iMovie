package com.example.imovie.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.imovie.domain.model.MovieModel
import com.example.imovie.domain.model.SectionModel
import com.example.imovie.domain.usecase.GetHomeListUseCase
import com.example.imovie.presentation.HomeViewAction
import com.example.imovie.presentation.HomeViewState
import com.example.imovie.presentation.mapper.MovieModelToUiModelMapper
import com.example.imovie.presentation.mapper.SectionModelToUiModelMapper
import com.example.imovie.presentation.model.MovieUiModel
import com.example.imovie.presentation.model.SectionUiModel
import com.example.imovie.utils.CoroutinesTestRule
import com.example.imovie.utils.NetworkError
import com.example.imovie.utils.Result
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantTest = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val getHomeListUseCase: GetHomeListUseCase = mockk()
    private val movieUiModelMapper = mockk<MovieModelToUiModelMapper>()
    private val sectionUiModelMapper: SectionModelToUiModelMapper = mockk()
    private val homeViewState: HomeViewState = spyk()
    private val homeViewModel = HomeViewModel(
        getHomeListUseCase = getHomeListUseCase,
        movieUiModelMapper = movieUiModelMapper,
        sectionUiModelMapper = sectionUiModelMapper,
        viewState = homeViewState
    )

    @Test
    fun dispatchViewAction_OnHomeInitializedAndGetHomeListSuccess_shouldUpdateHomeResultWithSuccess() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val sectionModelList = listOf(
                SectionModel(
                    id = "1",
                    titleSection = "sectionTest",
                    listMovies = emptyList()
                )
            )

            val sectionUiModelList = listOf(
                SectionUiModel(
                    id = "1",
                    titleSection = "sectionTest",
                    listMovies = emptyList()
                )
            )

            val expected = HomeResult.Success(sectionUiModelList)

            prepareScenario(
                homeListResult = Result.Success(sectionModelList),
                sectionUiModelMapperResult = sectionUiModelList
            )

            homeViewModel.dispatchViewAction(HomeViewAction.OnHomeInitialized)

            assertEquals(expected = expected, actual = homeViewModel.viewState.homeResult.value)
        }
    }

    @Test
    fun dispatchViewAction_OnHomeInitializedAndGetHomeListSuccess_shouldCallMovieUiModelMapper() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val movieModelList = listOf(
                MovieModel(
                    id = "1",
                    posterPath = "posterPathTest"
                )
            )

            val sectionModelList = listOf(
                SectionModel(
                    id = "1",
                    titleSection = "sectionTest",
                    listMovies = movieModelList
                )
            )

            prepareScenario(homeListResult = Result.Success(sectionModelList))

            homeViewModel.dispatchViewAction(HomeViewAction.OnHomeInitialized)

            verify(exactly = 1) {
                movieUiModelMapper.mapFrom(any())
            }
        }
    }

    @Test
    fun dispatchViewAction_OnHomeInitializedAndGetHomeListSuccess_shouldUpdateHomeHeaderResult() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val movieModelList = listOf(
                MovieModel(
                    id = "1",
                    posterPath = "posterPathTest"
                )
            )

            val sectionModelList = listOf(
                SectionModel(
                    id = "1",
                    titleSection = "sectionTest",
                    listMovies = movieModelList
                )
            )

            val movieUiModelMapperResult = MovieUiModel(
                id = "1",
                posterPath = "posterPathTest"
            )

            prepareScenario(
                homeListResult = Result.Success(sectionModelList),
                movieUiModelMapperResult = movieUiModelMapperResult
            )

            homeViewModel.dispatchViewAction(HomeViewAction.OnHomeInitialized)

            val expected = MovieUiModel(
                id = "1",
                posterPath = "posterPathTest"
            )

            assertEquals(
                expected = expected,
                actual = homeViewModel.viewState.homeHeaderResult.value
            )
        }
    }

    @Test
    fun dispatchViewAction_OnHomeInitializedAndGetHomeListSuccess_shouldNotUpdateHomeHeaderResult() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val sectionModelList = listOf(
                SectionModel(
                    id = "1",
                    titleSection = "sectionTest",
                    listMovies = emptyList()
                )
            )

            prepareScenario(homeListResult = Result.Success(sectionModelList))

            homeViewModel.dispatchViewAction(HomeViewAction.OnHomeInitialized)

            assertNull(actual = homeViewModel.viewState.homeHeaderResult.value)
        }
    }

    @Test
    fun dispatchViewAction_OnHomeInitializedAndGetHomeListSuccess_shouldCallSectionUiModelMapper() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val sectionModelList = listOf(
                SectionModel(
                    id = "1",
                    titleSection = "sectionTest",
                    listMovies = emptyList()
                )
            )

            prepareScenario(homeListResult = Result.Success(sectionModelList))

            homeViewModel.dispatchViewAction(HomeViewAction.OnHomeInitialized)

            verify(exactly = 1) {
                sectionUiModelMapper.mapFrom(any())
            }
        }
    }

    @Test
    fun dispatchViewAction_OnHomeInitializedAndGetHomeListError_shouldUpdateHomeResultWithError() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            prepareScenario(homeListResult = Result.Error(NetworkError()))

            homeViewModel.dispatchViewAction(HomeViewAction.OnHomeInitialized)

            val expected = HomeResult.Error

            assertEquals(expected = expected, actual = homeViewModel.viewState.homeResult.value)
        }
    }

    @Test
    fun dispatchViewAction_OnHomeMovieClickedAndMovieUiModelIsNotNull_shouldUpdateViewStateWithOpenDetails() {
        homeViewModel.viewState.homeHeaderResult.value = MovieUiModel(
            id = "1",
            posterPath = "posterPathTest"
        )

        homeViewModel.dispatchViewAction(HomeViewAction.OnHomeMovieClicked)

        assertTrue(homeViewModel.viewState.action.value is HomeViewState.Action.OpenDetails)
    }

    @Test
    fun dispatchViewAction_OnHomeMovieClickedAndMovieUiModelIsNull_shouldNotUpdateViewStateWithOpenDetails() {
        homeViewModel.viewState.homeHeaderResult.value = null

        homeViewModel.dispatchViewAction(HomeViewAction.OnHomeMovieClicked)

        assertFalse(homeViewModel.viewState.action.value is HomeViewState.Action.OpenDetails)
    }

    @Test
    fun dispatchViewAction_OnCarouselHomeMovieClickedAndMovieIdIsNotNull_shouldUpdateViewStateWithOpenDetails() {

        homeViewModel.dispatchViewAction(HomeViewAction.OnCarouselHomeMovieClicked("1"))

        assertTrue(homeViewModel.viewState.action.value is HomeViewState.Action.OpenDetails)

    }

    @Test
    fun dispatchViewAction_OnFavoriteMoviesClicked_shouldUpdateViewStateWithOpenFavorites() {

        homeViewModel.dispatchViewAction(HomeViewAction.OnFavoriteMoviesClicked)

        assertTrue(homeViewModel.viewState.action.value is HomeViewState.Action.OpenFavorites)
    }

    private fun prepareScenario(
        movieUiModelMapperResult: MovieUiModel = MovieUiModel(
            id = "1",
            posterPath = "posterPathTest"
        ),
        sectionUiModelMapperResult: List<SectionUiModel> = listOf(
            SectionUiModel(
                id = "1",
                titleSection = "sessaoTest",
                listMovies = listOf(movieUiModelMapperResult)
            )
        ),
        homeListResult: Result<List<SectionModel>, NetworkError> = Result.Success(emptyList())
    ) {
        coEvery {
            getHomeListUseCase.getHomeList()
        } returns homeListResult

        every {
            movieUiModelMapper.mapFrom(any())
        } returns movieUiModelMapperResult

        every {
            sectionUiModelMapper.mapFrom(any())
        } returns sectionUiModelMapperResult
    }
}