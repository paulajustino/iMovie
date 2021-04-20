package com.example.imovie.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imovie.presentation.model.MovieDetailsUiModel
import com.example.imovie.domain.usecase.GetDetailsUseCase
import com.example.imovie.presentation.mapper.MovieDetailsModelToUiModelMapper
import com.example.imovie.utils.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class DetailsResult {
    object Loading : DetailsResult()
    object Error : DetailsResult()

    data class Success(val movie: MovieDetailsUiModel) : DetailsResult()
}

class DetailsViewModel @Inject constructor(
    private val getDetailsUseCase: GetDetailsUseCase,
    private val movieDetailsUiModelMapper: MovieDetailsModelToUiModelMapper
) : ViewModel() {

    val detailsResult = MutableLiveData<DetailsResult>()

    fun fetch(id: String) {
        getMovieDetails(id)
    }

    private fun getMovieDetails(id: String) {
        detailsResult.value = DetailsResult.Loading
        viewModelScope.launch {
            val result = getDetailsUseCase.getDetails(id)

            detailsResult.value = when (result) {
                is Result.Success -> {
                    DetailsResult.Success(movieDetailsUiModelMapper.mapFrom(result.value))
                }
                is Result.Error -> DetailsResult.Error
            }
        }
    }
}