package com.example.imovie.di

import com.example.imovie.utils.DefaultDispatcherProvider
import com.example.imovie.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
object ProviderModule {
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    @JvmStatic
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    @JvmStatic
    @Provides
    fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()
}