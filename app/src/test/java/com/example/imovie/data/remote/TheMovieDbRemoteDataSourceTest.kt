package com.example.imovie.data.remote

import com.example.imovie.data.api.MovieDetailsResponse
import com.example.imovie.data.api.MovieListResponse
import com.example.imovie.data.api.MovieResponse
import com.example.imovie.data.api.TheMovieDbApiService
import com.example.imovie.data.mapper.MovieDetailsResponseToMovieDetailsModelMapper
import com.example.imovie.data.mapper.MovieListResponseToMovieModelMapper
import com.example.imovie.domain.model.MovieDetailsModel
import com.example.imovie.domain.model.MovieModel
import com.example.imovie.utils.CoroutinesTestRule
import com.example.imovie.utils.NetworkError
import com.example.imovie.utils.Result
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class TheMovieDbRemoteDataSourceTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val theMovieDbApiService: TheMovieDbApiService = mockk()
    private val movieListMapper: MovieListResponseToMovieModelMapper = mockk()
    private val movieDetailsMapper: MovieDetailsResponseToMovieDetailsModelMapper = mockk()
    private val theMovieDbRemoteDataSource = TheMovieDbDefaultRemoteDataSource(
        theMovieDbApiService = theMovieDbApiService,
        movieListMapper = movieListMapper,
        movieDetailsMapper = movieDetailsMapper,
        dispatcherProvider = coroutinesTestRule.testDispatcherProvider
    )

    @Test
    fun getPopularMovies_ResponseSuccess_shouldReturnResultWithSuccess() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val movieResponse = MovieResponse(
                id = 1,
                posterPath = "posterTest"
            )

            val movieModel = MovieModel(
                id = "1",
                posterPath = "posterTest"
            )

            prepareScenario(
                popularMoviesResult = Response.success(MovieListResponse(listOf(movieResponse))),
                movieListMapperResult = listOf(movieModel)
            )

            val expected = Result.Success(listOf(movieModel))
            val actual = theMovieDbRemoteDataSource.getPopularMovies()

            assertEquals(expected = expected, actual = actual)
        }
    }

    @Test
    fun getNowPlayingMovies_ResponseSuccess_shouldReturnResultWithSuccess() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val movieResponse = MovieResponse(
                id = 1,
                posterPath = "posterTest"
            )

            val movieModel = MovieModel(
                id = "1",
                posterPath = "posterTest"
            )

            prepareScenario(
                nowPlayingMoviesResult = Response.success(MovieListResponse(listOf(movieResponse))),
                movieListMapperResult = listOf(movieModel)
            )

            val expected = Result.Success(listOf(movieModel))
            val actual = theMovieDbRemoteDataSource.getNowPlayingMovies()

            assertEquals(expected = expected, actual = actual)
        }
    }

    @Test
    fun getTopRatedMovies_ResponseSuccess_shouldReturnResultWithSuccess() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val movieResponse = MovieResponse(
                id = 1,
                posterPath = "posterTest"
            )

            val movieModel = MovieModel(
                id = "1",
                posterPath = "posterTest"
            )

            prepareScenario(
                topRatedMoviesResult = Response.success(MovieListResponse(listOf(movieResponse))),
                movieListMapperResult = listOf(movieModel)
            )

            val expected = Result.Success(listOf(movieModel))
            val actual = theMovieDbRemoteDataSource.getTopRatedMovies()

            assertEquals(expected = expected, actual = actual)
        }
    }

    @Test
    fun getUpcomingMovies_ResponseSuccess_shouldReturnResultWithSuccess() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val movieResponse = MovieResponse(
                id = 1,
                posterPath = "posterTest"
            )

            val movieModel = MovieModel(
                id = "1",
                posterPath = "posterTest"
            )

            prepareScenario(
                upcomingMoviesResult = Response.success(MovieListResponse(listOf(movieResponse))),
                movieListMapperResult = listOf(movieModel)
            )

            val expected = Result.Success(listOf(movieModel))
            val actual = theMovieDbRemoteDataSource.getUpcomingMovies()

            assertEquals(expected = expected, actual = actual)
        }
    }

    @Test
    fun getMovieDetails_ResponseSuccess_shouldReturnResultWithSuccess() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val movieDetailsResponse = MovieDetailsResponse(
                id = 1,
                backdropPath = null,
                title = "titleTest",
                overview = null,
                release = "2019-10-12",
                runtime = 140
            )

            val movieDetailsModel = MovieDetailsModel(
                id = "1",
                backdropPath = null,
                title = "titleTest",
                overview = null,
                release = "2019-10-12",
                runtime = 140
            )

            prepareScenario(
                movieDetailsResult = Response.success(movieDetailsResponse),
                movieDetailsMapperResult = movieDetailsModel
            )

            val expected = Result.Success(movieDetailsModel)
            val actual = theMovieDbRemoteDataSource.getMovieDetails("1")

            assertEquals(expected = expected, actual = actual)
        }
    }

    @Test
    fun getPopularMovies_ResponseSuccess_shouldCallMovieListResponseToMovieModelMapper() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val movieResponse = MovieResponse(
                id = 1,
                posterPath = "posterTest"
            )

            prepareScenario(
                popularMoviesResult = Response.success(MovieListResponse(listOf(movieResponse)))
            )

            theMovieDbRemoteDataSource.getPopularMovies()

            verify(exactly = 1) {
                movieListMapper.mapListFrom(any())
            }
        }
    }

    @Test
    fun getNowPlayingMovies_ResponseSuccess_shouldCallMovieListResponseToMovieModelMapper() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val movieResponse = MovieResponse(
                id = 1,
                posterPath = "posterTest"
            )

            prepareScenario(
                nowPlayingMoviesResult = Response.success(MovieListResponse(listOf(movieResponse)))
            )

            theMovieDbRemoteDataSource.getNowPlayingMovies()

            verify(exactly = 1) {
                movieListMapper.mapListFrom(any())
            }
        }
    }

    @Test
    fun getTopRatedMovies_ResponseSuccess_shouldCallMovieListResponseToMovieModelMapper() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val movieResponse = MovieResponse(
                id = 1,
                posterPath = "posterTest"
            )

            prepareScenario(
                topRatedMoviesResult = Response.success(MovieListResponse(listOf(movieResponse)))
            )

            theMovieDbRemoteDataSource.getTopRatedMovies()

            verify(exactly = 1) {
                movieListMapper.mapListFrom(any())
            }
        }
    }

    @Test
    fun getUpcomingMovies_ResponseSuccess_shouldCallMovieListResponseToMovieModelMapper() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val movieResponse = MovieResponse(
                id = 1,
                posterPath = "posterTest"
            )

            prepareScenario(
                upcomingMoviesResult = Response.success(MovieListResponse(listOf(movieResponse)))
            )

            theMovieDbRemoteDataSource.getUpcomingMovies()

            verify(exactly = 1) {
                movieListMapper.mapListFrom(any())
            }
        }
    }

    @Test
    fun getMovieDetails_ResponseSuccess_shouldCallMovieDetailsResponseToMovieDetailsModelMapper() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val movieDetailsResponse = MovieDetailsResponse(
                id = 1,
                backdropPath = null,
                title = "titleTest",
                overview = null,
                release = "2019-10-12",
                runtime = 140
            )

            prepareScenario(
                movieDetailsResult = Response.success(movieDetailsResponse)
            )

            theMovieDbRemoteDataSource.getMovieDetails("1")

            verify(exactly = 1) {
                movieDetailsMapper.mapFrom(any())
            }
        }
    }

    @Test
    fun getPopularMovies_ResponseError_shouldReturnResultWithError() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            prepareScenario(
                popularMoviesResult = Response.error(
                    400,
                    ResponseBody.create(null, "error")
                )
            )

            val expected = Result.Error(NetworkError())
            val actual = theMovieDbRemoteDataSource.getPopularMovies()

            assertEquals(expected = expected, actual = actual)
        }
    }

    @Test
    fun getNowPlayingMovies_ResponseError_shouldReturnResultWithError() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            prepareScenario(
                nowPlayingMoviesResult = Response.error(
                    400,
                    ResponseBody.create(null, "error")
                )
            )

            val expected = Result.Error(NetworkError())
            val actual = theMovieDbRemoteDataSource.getNowPlayingMovies()

            assertEquals(expected = expected, actual = actual)
        }
    }

    @Test
    fun getTopRatedMovies_ResponseError_shouldReturnResultWithError() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            prepareScenario(
                topRatedMoviesResult = Response.error(
                    400,
                    ResponseBody.create(null, "error")
                )
            )

            val expected = Result.Error(NetworkError())
            val actual = theMovieDbRemoteDataSource.getTopRatedMovies()

            assertEquals(expected = expected, actual = actual)
        }
    }

    @Test
    fun getUpcomingMovies_ResponseError_shouldReturnResultWithError() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            prepareScenario(
                upcomingMoviesResult = Response.error(
                    400,
                    ResponseBody.create(null, "error")
                )
            )

            val expected = Result.Error(NetworkError())
            val actual = theMovieDbRemoteDataSource.getUpcomingMovies()

            assertEquals(expected = expected, actual = actual)
        }
    }

    @Test
    fun getMovieDetails_ResponseSuccessWithNullBody_shouldReturnResultWithError() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            prepareScenario(
                movieDetailsResult = Response.success(null)
            )

            val expected = Result.Error(NetworkError())
            val actual = theMovieDbRemoteDataSource.getMovieDetails("1")

            assertEquals(expected = expected, actual = actual)
        }
    }

    private fun prepareScenario(
        movieListMapperResult: List<MovieModel> = emptyList(),
        movieDetailsMapperResult: MovieDetailsModel = MovieDetailsModel(
            id = "1",
            backdropPath = "backdropTest",
            title = "titleTest",
            overview = "overviewTest",
            release = "2019-10-12",
            runtime = 140
        ),
        movieDetailsResponse: MovieDetailsResponse = MovieDetailsResponse(
            id = 1,
            backdropPath = "backdropTest",
            title = "titleTest",
            overview = "overviewTest",
            release = "2019-10-12",
            runtime = 140
        ),
        movieResponse: MovieResponse = MovieResponse(
            id = 1,
            posterPath = "posterTest"
        ),
        movieListResponse: MovieListResponse = MovieListResponse(listOf(movieResponse)),
        popularMoviesResult: Response<MovieListResponse> = Response.success(movieListResponse),
        nowPlayingMoviesResult: Response<MovieListResponse> = Response.success(movieListResponse),
        topRatedMoviesResult: Response<MovieListResponse> = Response.success(movieListResponse),
        upcomingMoviesResult: Response<MovieListResponse> = Response.success(movieListResponse),
        movieDetailsResult: Response<MovieDetailsResponse> = Response.success(movieDetailsResponse)
    ) {
        coEvery {
            theMovieDbApiService.getPopularMovies(any(), any())
        } returns popularMoviesResult

        coEvery {
            theMovieDbApiService.getNowPlayingMovies(any(), any())
        } returns nowPlayingMoviesResult

        coEvery {
            theMovieDbApiService.getTopRatedMovies(any(), any())
        } returns topRatedMoviesResult

        coEvery {
            theMovieDbApiService.getUpcomingMovies(any(), any())
        } returns upcomingMoviesResult

        coEvery {
            theMovieDbApiService.getMovieDetails(any(), any(), any())
        } returns movieDetailsResult

        coEvery {
            movieListMapper.mapListFrom(any())
        } returns movieListMapperResult

        coEvery {
            movieDetailsMapper.mapFrom(any())
        } returns movieDetailsMapperResult
    }
}