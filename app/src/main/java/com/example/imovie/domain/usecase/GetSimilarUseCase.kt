package com.example.imovie.domain.usecase

import com.example.imovie.data.repository.TheMovieDbRepository
import com.example.imovie.domain.model.MovieModel
import com.example.imovie.utils.DispatcherProvider
import com.example.imovie.utils.NetworkError
import com.example.imovie.utils.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetSimilarUseCase {

    suspend fun getSimilar(movieId: String): Result<List<MovieModel>, NetworkError>
}

class GetSimilar @Inject constructor(
    private val theMovieDbRepository: TheMovieDbRepository,
    private val dispatcherProvider: DispatcherProvider
) : GetSimilarUseCase {

    override suspend fun getSimilar(movieId: String): Result<List<MovieModel>, NetworkError> {
        return withContext(dispatcherProvider.io()) {
            val similarMovies = async { theMovieDbRepository.getSimilarMovies(movieId) }
            val similarMoviesReturn = similarMovies.await()
            if (similarMoviesReturn is Result.Success && similarMoviesReturn.value.isNotEmpty()) {
                val similar = similarMoviesReturn.value
                return@withContext Result.Success(similar)
            } else {
                return@withContext Result.Error(NetworkError())
            }
        }
    }
}