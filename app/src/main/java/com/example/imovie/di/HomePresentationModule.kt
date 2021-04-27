package com.example.imovie.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.imovie.presentation.mapper.*
import com.example.imovie.presentation.viewmodel.DetailsViewModel
import com.example.imovie.presentation.viewmodel.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface HomePresentationModule {

    @[Binds IntoMap ViewModelKey(HomeViewModel::class)]
    fun bindsHomeViewModel(viewModel: HomeViewModel): ViewModel

    @[Binds IntoMap ViewModelKey(DetailsViewModel::class)]
    fun bindsDetailsViewModel(viewModel: DetailsViewModel): ViewModel

    @Binds
    fun bindsViewModelFactory(viewModelProviderFactory: ViewModelProviderFactory): ViewModelProvider.Factory

    @Binds
    fun bindsMovieModelToUiModelMapper(movieModelToUiModelDefaultMapper: MovieModelToUiModelDefaultMapper): MovieModelToUiModelMapper

    @Binds
    fun bindsSectionModelToUiModelMapper(sectionModelToUiModelDefaultMapper: SectionModelToUiModelDefaultMapper): SectionModelToUiModelMapper

    @Binds
    fun bindsMovieDetailsModelToUiModelMapper(movieDetailsModelToUiModelDefaultMapper: MovieDetailsModelToUiModelDefaultMapper): MovieDetailsModelToUiModelMapper
}