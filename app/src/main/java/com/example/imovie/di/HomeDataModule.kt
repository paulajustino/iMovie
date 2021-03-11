package com.example.imovie.di

import com.example.imovie.data.mapper.MovieListResponseToMovieModelDefaultMapper
import com.example.imovie.data.mapper.MovieListResponseToMovieModelMapper
import com.example.imovie.data.mapper.MovieResponseToMovieModelDefaultMapper
import com.example.imovie.data.mapper.MovieResponseToMovieModelMapper
import com.example.imovie.data.remote.TheMovieDbDefaultRemoteDataSource
import com.example.imovie.data.remote.TheMovieDbRemoteDataSource
import com.example.imovie.data.repository.TheMovieDbDefaultRepository
import com.example.imovie.data.repository.TheMovieDbRepository
import dagger.Binds
import dagger.Module

@Module
interface HomeDataModule {

    @Binds
    fun bindsTheMovieDbRepository(theMovieDbDefaultRepository: TheMovieDbDefaultRepository): TheMovieDbRepository

    @Binds
    fun bindsTheMovieDbRemoteDataSource(theMovieDbDefaultRemoteDataSource: TheMovieDbDefaultRemoteDataSource): TheMovieDbRemoteDataSource

    @Binds
    fun bindsMovieResponseToMovieModelMapper(movieResponseToMovieModelDefaultMapper: MovieResponseToMovieModelDefaultMapper): MovieResponseToMovieModelMapper

    @Binds
    fun bindsMovieListResponseToMovieModelMapper(movieListResponseToMovieModelDefaultMapper: MovieListResponseToMovieModelDefaultMapper): MovieListResponseToMovieModelMapper
}