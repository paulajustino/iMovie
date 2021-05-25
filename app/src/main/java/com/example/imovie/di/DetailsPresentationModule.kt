package com.example.imovie.di

import androidx.lifecycle.ViewModel
import com.example.imovie.presentation.mapper.MovieDetailsModelToUiModelDefaultMapper
import com.example.imovie.presentation.mapper.MovieDetailsModelToUiModelMapper
import com.example.imovie.presentation.mapper.MovieListModelToUiModelDefaultMapper
import com.example.imovie.presentation.mapper.MovieListModelToUiModelMapper
import com.example.imovie.presentation.viewmodel.DetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface DetailsPresentationModule {

    @[Binds IntoMap ViewModelKey(DetailsViewModel::class)]
    fun bindsDetailsViewModel(viewModel: DetailsViewModel): ViewModel

    @Binds
    fun bindsMovieDetailsModelToUiModelMapper(movieDetailsModelToUiModelDefaultMapper: MovieDetailsModelToUiModelDefaultMapper): MovieDetailsModelToUiModelMapper

    @Binds
    fun bindsMovieListModelToUiModelMapper(movieListModelToUiModelMapper: MovieListModelToUiModelDefaultMapper): MovieListModelToUiModelMapper
}