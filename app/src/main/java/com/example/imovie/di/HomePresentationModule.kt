package com.example.imovie.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.imovie.presentation.mapper.MovieModelToUiModelDefaultMapper
import com.example.imovie.presentation.mapper.MovieModelToUiModelMapper
import com.example.imovie.presentation.mapper.SectionModelToUiModelDefaultMapper
import com.example.imovie.presentation.mapper.SectionModelToUiModelMapper
import com.example.imovie.presentation.viewmodel.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface HomePresentationModule {

    @[Binds IntoMap ViewModelKey(HomeViewModel::class)]
    fun bindsHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    fun bindsViewModelFactory(viewModelProviderFactory: ViewModelProviderFactory): ViewModelProvider.Factory

    @Binds
    fun bindsMovieModelToUiModelMapper(movieModelToUiModelDefaultMapper: MovieModelToUiModelDefaultMapper): MovieModelToUiModelMapper

    @Binds
    fun bindsSectionModelToUiModelMapper(sectionModelToUiModelDefaultMapper: SectionModelToUiModelDefaultMapper): SectionModelToUiModelMapper
}