package com.example.imovie.data.repository

import com.example.imovie.data.remote.TheMovieDbRemoteDataSource
import com.example.imovie.domain.model.MovieDetailsModel
import com.example.imovie.domain.model.MovieModel
import com.example.imovie.utils.CoroutinesTestRule
import com.example.imovie.utils.NetworkError
import com.example.imovie.utils.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TheMovieDbRepositoryTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val remoteDataSource: TheMovieDbRemoteDataSource = mockk()
    private val theMovieDbRepository = TheMovieDbDefaultRepository(
        remoteDataSource = remoteDataSource
    )

    @Test
    fun getPopularMovies_shouldCallGetPopularMoviesFromRemoteDataSource() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            prepareScenario(
                movieModelList = listOf(
                    MovieModel(
                        id = "1",
                        posterPath = "posterTest"
                    )
                )
            )

            theMovieDbRepository.getPopularMovies()

            coVerify(exactly = 1) {
                remoteDataSource.getPopularMovies()
            }
        }
    }

    @Test
    fun getNowPlayingMovies_shouldCallGetNowPlayingMoviesFromRemoteDataSource() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            prepareScenario(
                movieModelList = listOf(
                    MovieModel(
                        id = "1",
                        posterPath = "posterTest"
                    )
                )
            )

            theMovieDbRepository.getNowPlayingMovies()

            coVerify(exactly = 1) {
                remoteDataSource.getNowPlayingMovies()
            }
        }
    }

    @Test
    fun getTopRatedMovies_shouldCallGetTopRatedMoviesFromRemoteDataSource() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            prepareScenario(
                movieModelList = listOf(
                    MovieModel(
                        id = "1",
                        posterPath = "posterTest"
                    )
                )
            )

            theMovieDbRepository.getTopRatedMovies()

            coVerify(exactly = 1) {
                remoteDataSource.getTopRatedMovies()
            }
        }
    }

    @Test
    fun getUpcomingMovies_shouldCallGetUpcomingMoviesFromRemoteDataSource() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            prepareScenario(
                movieModelList = listOf(
                    MovieModel(
                        id = "1",
                        posterPath = "posterTest"
                    )
                )
            )

            theMovieDbRepository.getUpcomingMovies()

            coVerify(exactly = 1) {
                remoteDataSource.getUpcomingMovies()
            }
        }
    }

    @Test
    fun getMovieDetails_shouldCallGetMovieDetailsFromRemoteDataSource() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            prepareScenario(
                movieDetailsModel = MovieDetailsModel(
                    id = "1",
                    backdropPath = "backdropTest",
                    title = "titleTest",
                    overview = "overviewTest",
                    release = "2019-10-12",
                    runtime = 140

                )
            )

            theMovieDbRepository.getMovieDetails("1")

            coVerify(exactly = 1) {
                remoteDataSource.getMovieDetails(any())
            }
        }
    }

    private fun prepareScenario(
        movieModelList: List<MovieModel> = listOf(
            MovieModel(
                id = "1",
                posterPath = "posterTest"
            )
        ),
        movieDetailsModel: MovieDetailsModel? = MovieDetailsModel(
            id = "1",
            backdropPath = "backdropTest",
            title = "titleTest",
            overview = "overviewTest",
            release = "2019-10-12",
            runtime = 140
        ),
        popularMoviesResult: Result<List<MovieModel>, NetworkError> = Result.Success(movieModelList),
        nowPlayingMoviesResult: Result<List<MovieModel>, NetworkError> = Result.Success(
            movieModelList
        ),
        topRatedMoviesResult: Result<List<MovieModel>, NetworkError> = Result.Success(movieModelList),
        upcomingMoviesResult: Result<List<MovieModel>, NetworkError> = Result.Success(movieModelList),
        movieDetailsResult: Result<MovieDetailsModel?, NetworkError> = Result.Success(
            movieDetailsModel
        )
    ) {
        coEvery {
            remoteDataSource.getPopularMovies()
        } returns popularMoviesResult

        coEvery {
            remoteDataSource.getNowPlayingMovies()
        } returns nowPlayingMoviesResult

        coEvery {
            remoteDataSource.getTopRatedMovies()
        } returns topRatedMoviesResult

        coEvery {
            remoteDataSource.getUpcomingMovies()
        } returns upcomingMoviesResult

        coEvery {
            remoteDataSource.getMovieDetails(any())
        } returns movieDetailsResult
    }
}