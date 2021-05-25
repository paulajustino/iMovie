package com.example.imovie.domain.usecase

import com.example.imovie.data.repository.TheMovieDbRepository
import com.example.imovie.domain.model.MovieModel
import com.example.imovie.utils.DispatcherProvider
import com.example.imovie.utils.NetworkError
import com.example.imovie.utils.Result
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetSimilarMoviesUseCase {

    suspend fun getSimilarMovies(movieId: String): Result<List<MovieModel>, NetworkError>
}

class GetSimilarMovies @Inject constructor(
    private val theMovieDbRepository: TheMovieDbRepository,
    private val dispatcherProvider: DispatcherProvider
) : GetSimilarMoviesUseCase {

    override suspend fun getSimilarMovies(movieId: String): Result<List<MovieModel>, NetworkError> {
        return withContext(dispatcherProvider.io()) {
            val similarMovies = theMovieDbRepository.getSimilarMovies(movieId)
            if (similarMovies is Result.Success && similarMovies.value.isNotEmpty()) {
                val similar = similarMovies.value
                return@withContext Result.Success(similar)
            } else {
                return@withContext Result.Error(NetworkError())
            }
        }
    }
}