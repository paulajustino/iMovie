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
                    titleSection = "sectionTeste",
                    listMovies = emptyList()
                )
            )

            val sectionUiModelList = listOf(
                SectionUiModel(
                    id = "1",
                    titleSection = "sectionTeste",
                    listMovies = emptyList()
                )
            )

            val expected = HomeResult.Success(sectionUiModelList)

            prepareScenario(
                homeListResult = Result.Success(sectionModelList),
                sectionUiModelMapperResult = sectionUiModelList
            )

            homeViewModel.dispatchViewAction(HomeViewAction.OnHomeInitialized)

            assertEquals(expected = expected, actual = homeViewModel.homeResult.value)
        }
    }

    @Test
    fun dispatchViewAction_OnHomeInitializedAndGetHomeListSuccess_shouldCallMovieUiModelMapper() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val movieModelList = listOf(
                MovieModel(
                    id = "1",
                    posterPath = "posterPathTeste"
                )
            )

            val sectionModelList = listOf(
                SectionModel(
                    id = "1",
                    titleSection = "sectionTeste",
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
                    posterPath = "posterPathTeste"
                )
            )

            val sectionModelList = listOf(
                SectionModel(
                    id = "1",
                    titleSection = "sectionTeste",
                    listMovies = movieModelList
                )
            )

            val movieUiModelMapperResult = MovieUiModel(
                id = "1",
                posterPath = "posterPathTeste"
            )

            prepareScenario(
                homeListResult = Result.Success(sectionModelList),
                movieUiModelMapperResult = movieUiModelMapperResult
            )

            homeViewModel.dispatchViewAction(HomeViewAction.OnHomeInitialized)

            val expected = MovieUiModel(
                id = "1",
                posterPath = "posterPathTeste"
            )

            assertEquals(expected = expected, actual = homeViewModel.homeHeaderResult.value)
        }
    }

    @Test
    fun dispatchViewAction_OnHomeInitializedAndGetHomeListSuccess_shouldNotUpdateHomeHeaderResult() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val sectionModelList = listOf(
                SectionModel(
                    id = "1",
                    titleSection = "sectionTeste",
                    listMovies = emptyList()
                )
            )

            prepareScenario(homeListResult = Result.Success(sectionModelList))

            homeViewModel.dispatchViewAction(HomeViewAction.OnHomeInitialized)

            assertNull(actual = homeViewModel.homeHeaderResult.value)
        }
    }

    @Test
    fun dispatchViewAction_OnHomeInitializedAndGetHomeListSuccess_shouldCallSectionUiModelMapper() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val sectionModelList = listOf(
                SectionModel(
                    id = "1",
                    titleSection = "sectionTeste",
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
            prepareScenario(homeListResult = Result.Error(NetworkError(null)))

            homeViewModel.dispatchViewAction(HomeViewAction.OnHomeInitialized)

            val expected = HomeResult.Error

            assertEquals(expected = expected, actual = homeViewModel.homeResult.value)
        }
    }

    @Test
    fun dispatchViewAction_OnHomeMovieClickedAndMovieUiModelIsNotNull_shouldUpdateViewStateWithOpenDetails() {
        homeViewModel.homeHeaderResult.value = MovieUiModel(
            id = "1",
            posterPath = "posterPathTeste"
        )

        homeViewModel.dispatchViewAction(HomeViewAction.OnHomeMovieClicked)

        assertTrue(homeViewModel.viewState.action.value is HomeViewState.Action.OpenDetails)
    }

    @Test
    fun dispatchViewAction_OnHomeMovieClickedAndMovieUiModelIsNull_shouldNotUpdateViewStateWithOpenDetails() {
        homeViewModel.homeHeaderResult.value = null

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
            posterPath = "posterPathTeste"
        ),
        sectionUiModelMapperResult: List<SectionUiModel> = listOf(
            SectionUiModel(
                id = "1",
                titleSection = "sessaoTeste",
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