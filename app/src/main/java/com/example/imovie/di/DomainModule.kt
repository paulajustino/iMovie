package com.example.imovie.di

import com.example.imovie.domain.usecase.*
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @Binds
    fun bindsGetHomeListUseCase(getHomeList: GetHomeList): GetHomeListUseCase

    @Binds
    fun bindsGetDetailsUseCase(getDetails: GetDetails): GetDetailsUseCase

    @Binds
    fun bindsGetSimilarUseCase(getSimilar: GetSimilar): GetSimilarUseCase
}