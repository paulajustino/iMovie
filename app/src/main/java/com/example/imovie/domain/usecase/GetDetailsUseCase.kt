package com.example.imovie.domain.usecase

import com.example.imovie.data.repository.TheMovieDbRepository
import com.example.imovie.domain.model.MovieDetailsModel
import com.example.imovie.utils.DispatcherProvider
import com.example.imovie.utils.NetworkError
import com.example.imovie.utils.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetDetailsUseCase {

    suspend fun getDetails(movieId: String): Result<MovieDetailsModel, NetworkError>
}

class GetDetails @Inject constructor(
    private val theMovieDbRepository: TheMovieDbRepository,
    private val dispatcherProvider: DispatcherProvider
) : GetDetailsUseCase {

    override suspend fun getDetails(movieId: String): Result<MovieDetailsModel, NetworkError> {
        return withContext(dispatcherProvider.io()) {
            val movieDetails = async { theMovieDbRepository.getMovieDetails(movieId) }
            val movieDetailsReturn = movieDetails.await()
            if (movieDetailsReturn is Result.Success && movieDetailsReturn.value != null) {
                val details = movieDetailsReturn.value
                return@withContext Result.Success(details)
            } else {
                return@withContext Result.Error(NetworkError())
            }
        }
    }
}
