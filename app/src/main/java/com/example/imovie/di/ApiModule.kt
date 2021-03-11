package com.example.imovie.di

import com.example.imovie.data.api.TheMovieDbApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object ApiModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideMovieApiService(retrofit: Retrofit): TheMovieDbApiService {
        return retrofit.create(TheMovieDbApiService::class.java)
    }
}