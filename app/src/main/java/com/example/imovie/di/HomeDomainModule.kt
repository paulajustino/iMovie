package com.example.imovie.di

import com.example.imovie.domain.usecase.GetDetails
import com.example.imovie.domain.usecase.GetDetailsUseCase
import com.example.imovie.domain.usecase.GetHomeList
import com.example.imovie.domain.usecase.GetHomeListUseCase
import dagger.Binds
import dagger.Module

@Module
interface HomeDomainModule {

    @Binds
    fun bindsGetHomeListUseCase(getHomeList: GetHomeList): GetHomeListUseCase

    @Binds
    fun bindsGetDetailsUseCase(getDetails: GetDetails): GetDetailsUseCase
}