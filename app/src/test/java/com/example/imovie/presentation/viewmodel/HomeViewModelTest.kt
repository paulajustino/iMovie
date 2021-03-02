package com.example.imovie.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.imovie.*
import com.example.imovie.domain.usecase.GetHomeListUseCase
import com.example.imovie.presentation.mapper.MovieModelToUiModelMapper
import com.example.imovie.presentation.mapper.SectionModelToUiModelMapper
import com.example.imovie.utils.Result
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantTest = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val getHomeListUseCase: GetHomeListUseCase = mockk()
    private val movieUiModelMapper = mockk<MovieModelToUiModelMapper>()
    private val sectionUiModelMapper: SectionModelToUiModelMapper = mockk()
    private val homeViewModel = HomeViewModel(
        getHomeListUseCase = getHomeListUseCase,
        movieUiModelMapper = movieUiModelMapper,
        sectionUiModelMapper = sectionUiModelMapper
    )

    @Test
    fun fetch_getHomeListSuccess_shouldUpdateHomeResultWithSuccess() {
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

            prepareScenario(
                homeListResult = Result.Success(sectionModelList),
                sectionUiModelMapperResult = sectionUiModelList
            )

            homeViewModel.fetch()

            val expected = HomeResult.Success(sectionUiModelList)

            assertEquals(expected, homeViewModel.homeResult.value)
        }
    }

    @Test
    fun fetch_getHomeListSuccess_shouldCallMovieUiModelMapper() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val movieModelList = listOf(
                MovieModel(
                    id = "1",
                    titleMovie = "titleTeste",
                    posterPath = "posterPathTeste",
                    descriptionMovie = "descriptionTeste"
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

            homeViewModel.fetch()

            verify(exactly = 1) {
                movieUiModelMapper.mapFrom(any())
            }
        }
    }

    @Test
    fun fetch_getHomeListSuccess_shouldUpdateHomeHeaderResult() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val movieModelList = listOf(
                MovieModel(
                    id = "1",
                    titleMovie = "titleTeste",
                    posterPath = "posterPathTeste",
                    descriptionMovie = "descriptionTeste"
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

            homeViewModel.fetch()

            val expected = MovieUiModel(
                id = "1",
                posterPath = "posterPathTeste"
            )

            assertEquals(expected, homeViewModel.homeHeaderResult.value)
        }
    }

    @Test
    fun fetch_getHomeListSuccess_shouldNotUpdateHomeHeaderResult() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val sectionModelList = listOf(
                SectionModel(
                    id = "1",
                    titleSection = "sectionTeste",
                    listMovies = emptyList()
                )
            )

            prepareScenario(homeListResult = Result.Success(sectionModelList))

            homeViewModel.fetch()

            val expected = null

            assertEquals(expected, homeViewModel.homeHeaderResult.value)
        }
    }

    @Test
    fun fetch_getHomeListSuccess_shouldCallSectionUiModelMapper() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val sectionModelList = listOf(
                SectionModel(
                    id = "1",
                    titleSection = "sectionTeste",
                    listMovies = emptyList()
                )
            )

            prepareScenario(homeListResult = Result.Success(sectionModelList))

            homeViewModel.fetch()

            verify(exactly = 1) {
                sectionUiModelMapper.mapFrom(any())
            }
        }
    }

    @Test
    fun fetch_getHomeListError_shouldUpdateHomeResultWithError() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            prepareScenario(homeListResult = Result.Error(NetworkError()))

            homeViewModel.fetch()

            val expected = HomeResult.Error

            assertEquals(expected, homeViewModel.homeResult.value)
        }
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