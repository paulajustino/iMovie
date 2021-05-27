package com.example.imovie.domain

import com.example.imovie.data.repository.TheMovieDbRepository
import com.example.imovie.domain.model.MovieModel
import com.example.imovie.domain.model.SectionModel
import com.example.imovie.domain.usecase.GetHomeList
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
class GetHomeListUseCaseTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val theMovieDbRepository: TheMovieDbRepository = mockk()
    private val getHomeListUseCase = GetHomeList(
        theMovieDbRepository = theMovieDbRepository,
        dispatcherProvider = coroutinesTestRule.testDispatcherProvider
    )

    @Test
    fun getHomeList_AllResultSuccessWithMovieList_shouldReturnResultWithSuccess() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val popularMovieModel = MovieModel(
                id = "1",
                posterPath = "popularPosterTeste"
            )

            val nowPlayingMovieModel = MovieModel(
                id = "2",
                posterPath = "nowPlayingPosterTeste"
            )

            val topRatedMovieModel = MovieModel(
                id = "3",
                posterPath = "topRatedPosterTeste"
            )

            val upcomingMovieModel = MovieModel(
                id = "4",
                posterPath = "upcomingPosterTeste"
            )

            prepareScenario(
                popularMovieResult = Result.Success(listOf(popularMovieModel)),
                nowPlayingMoviesResult = Result.Success(listOf(nowPlayingMovieModel)),
                topRatedMoviesResult = Result.Success(listOf(topRatedMovieModel)),
                upcomingMoviesResult = Result.Success(listOf(upcomingMovieModel))
            )

            val popularSection = SectionModel(
                id = "1",
                titleSection = "Populares",
                listMovies = listOf(popularMovieModel)
            )

            val nowPlayingSection = SectionModel(
                id = "2",
                titleSection = "Agora nos Cinemas",
                listMovies = listOf(nowPlayingMovieModel)
            )

            val topRatedSection = SectionModel(
                id = "3",
                titleSection = "Mais Votados",
                listMovies = listOf(topRatedMovieModel)
            )

            val upcomingSection = SectionModel(
                id = "4",
                titleSection = "Próximos Lançamentos",
                listMovies = listOf(upcomingMovieModel)
            )

            val listSection = listOf(
                popularSection,
                nowPlayingSection,
                topRatedSection,
                upcomingSection
            )

            val expected = Result.Success(listSection)
            val actual = getHomeListUseCase.getHomeList()

            assertEquals(expected = expected, actual = actual)
        }
    }

    @Test
    fun getHomeList_SomeResultSuccessWithMovieList_shouldReturnResultWithSuccess() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val popularMovieModel = MovieModel(
                id = "1",
                posterPath = "popularPosterTeste"
            )

            val topRatedMovieModel = MovieModel(
                id = "2",
                posterPath = "topRatedPosterTeste"
            )

            prepareScenario(
                popularMovieResult = Result.Success(listOf(popularMovieModel)),
                nowPlayingMoviesResult = Result.Success(emptyList()),
                topRatedMoviesResult = Result.Success(listOf(topRatedMovieModel)),
                upcomingMoviesResult = Result.Success(emptyList())
            )

            val popularSection = SectionModel(
                id = "1",
                titleSection = "Populares",
                listMovies = listOf(popularMovieModel)
            )

            val topRatedSection = SectionModel(
                id = "3",
                titleSection = "Mais Votados",
                listMovies = listOf(topRatedMovieModel)
            )

            val listSection = listOf(
                popularSection,
                topRatedSection
            )

            val expected = Result.Success(listSection)
            val actual = getHomeListUseCase.getHomeList()

            assertEquals(expected = expected, actual = actual)
        }
    }

    @Test
    fun getHomeList_SomeResultSuccessWithPosterPathNull_shouldReturnResultWithSuccess() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val popularMovieModel = MovieModel(
                id = "1",
                posterPath = null
            )

            val topRatedMovieModel = MovieModel(
                id = "2",
                posterPath = "topRatedPosterTeste"
            )

            prepareScenario(
                popularMovieResult = Result.Success(listOf(popularMovieModel)),
                nowPlayingMoviesResult = Result.Success(emptyList()),
                topRatedMoviesResult = Result.Success(listOf(topRatedMovieModel)),
                upcomingMoviesResult = Result.Success(emptyList())
            )

            val topRatedSection = SectionModel(
                id = "3",
                titleSection = "Mais Votados",
                listMovies = listOf(topRatedMovieModel)
            )

            val listSection = listOf(
                topRatedSection
            )

            val expected = Result.Success(listSection)
            val actual = getHomeListUseCase.getHomeList()

            assertEquals(expected = expected, actual = actual)
        }
    }

    @Test
    fun getHomeList_AllResultSuccessWithEmptyLists_shouldReturnResultWithError() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            prepareScenario(
                popularMovieResult = Result.Success(emptyList()),
                nowPlayingMoviesResult = Result.Success(emptyList()),
                topRatedMoviesResult = Result.Success(emptyList()),
                upcomingMoviesResult = Result.Success(emptyList())
            )

            val expected = Result.Error(NetworkError())
            val actual = getHomeListUseCase.getHomeList()

            assertEquals(expected = expected, actual = actual)
        }
    }

    @Test
    fun getHomeList_AllResultError_shouldReturnResultWithError() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            prepareScenario(
                popularMovieResult = Result.Error(NetworkError()),
                nowPlayingMoviesResult = Result.Error(NetworkError()),
                topRatedMoviesResult = Result.Error(NetworkError()),
                upcomingMoviesResult = Result.Error(NetworkError())
            )

            val expected = Result.Error(NetworkError())
            val actual = getHomeListUseCase.getHomeList()

            assertEquals(expected = expected, actual = actual)
        }
    }

    private fun prepareScenario(
        movieModel: MovieModel = MovieModel(
            id = "1",
            posterPath = "posterTeste"
        ),
        popularMovieResult: Result<List<MovieModel>, NetworkError> = Result.Success(
            listOf(
                movieModel
            )
        ),
        nowPlayingMoviesResult: Result<List<MovieModel>, NetworkError> = Result.Success(
            listOf(
                movieModel
            )
        ),
        topRatedMoviesResult: Result<List<MovieModel>, NetworkError> = Result.Success(
            listOf(
                movieModel
            )
        ),
        upcomingMoviesResult: Result<List<MovieModel>, NetworkError> = Result.Success(
            listOf(
                movieModel
            )
        )
    ) {
        coEvery {
            theMovieDbRepository.getPopularMovies()
        } returns popularMovieResult

        coEvery {
            theMovieDbRepository.getNowPlayingMovies()
        } returns nowPlayingMoviesResult

        coEvery {
            theMovieDbRepository.getTopRatedMovies()
        } returns topRatedMoviesResult

        coEvery {
            theMovieDbRepository.getUpcomingMovies()
        } returns upcomingMoviesResult
    }
}