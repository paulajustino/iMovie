package com.example.imovie.domain.usecase

import com.example.imovie.*
import com.example.imovie.data.repository.TheMovieDbRepository
import com.example.imovie.utils.Result
import kotlinx.coroutines.*

class GetHomeListUseCase {
    private val theMovieDbRepository: TheMovieDbRepository = TheMovieDbRepository()

    suspend fun getHomeList(): Result<List<SectionModel>, NetworkError> {
        return withContext(Dispatchers.IO) {
            val popularMovies = async { theMovieDbRepository.getPopularMovies() }
            val nowPlayingMovies = async { theMovieDbRepository.getNowPlayingMovies() }
            val topRatedMovies = async { theMovieDbRepository.getTopRatedMovies() }
            val upcomingMovies = async { theMovieDbRepository.getUpcomingMovies() }

            val popularMoviesReturn = popularMovies.await().mapMovieListToSection("1", "Populares")
            val nowPlayingMoviesReturn =
                nowPlayingMovies.await().mapMovieListToSection("2", "Agora nos Cinemas")
            val topRatedMoviesReturn =
                topRatedMovies.await().mapMovieListToSection("3", "Mais Votados")
            val upcomingMoviesReturn =
                upcomingMovies.await().mapMovieListToSection("4", "Próximos Lançamentos")

            val returns = listOf(
                popularMoviesReturn, nowPlayingMoviesReturn,
                topRatedMoviesReturn, upcomingMoviesReturn
            ).mapNotNull {
                if (it is Result.Success && it.value.listMovies.isNotEmpty()) {
                    val movies = filterMoviesWithPosterPath(it.value.listMovies)
                    if (movies.isNotEmpty()) {
                        it.value.copy(listMovies = movies)
                    } else {
                        null
                    }
                } else {
                    null
                }
            }

            if (returns.isEmpty()) {
                return@withContext Result.Error(NetworkError())
            }

            return@withContext Result.Success(returns)
        }
    }

    private fun Result<List<MovieModel>, NetworkError>.mapMovieListToSection(
        sectionId: String,
        sectionTitle: String
    ): Result<SectionModel, NetworkError> {
        return when (this) {
            is Result.Success -> Result.Success(SectionModel(sectionId, sectionTitle, this.value))
            is Result.Error -> this
        }
    }

    private fun filterMoviesWithPosterPath(listMovies: List<MovieModel>): List<MovieModel> {
        return listMovies.filter { it.posterPath != null }
    }
}