package com.example.imovie.di

import com.example.imovie.data.mapper.*
import com.example.imovie.data.remote.TheMovieDbDefaultRemoteDataSource
import com.example.imovie.data.remote.TheMovieDbRemoteDataSource
import com.example.imovie.data.repository.TheMovieDbDefaultRepository
import com.example.imovie.data.repository.TheMovieDbRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    fun bindsTheMovieDbRepository(theMovieDbDefaultRepository: TheMovieDbDefaultRepository): TheMovieDbRepository

    @Binds
    fun bindsTheMovieDbRemoteDataSource(theMovieDbDefaultRemoteDataSource: TheMovieDbDefaultRemoteDataSource): TheMovieDbRemoteDataSource

    @Binds
    fun bindsMovieResponseToMovieModelMapper(movieResponseToMovieModelDefaultMapper: MovieResponseToMovieModelDefaultMapper): MovieResponseToMovieModelMapper

    @Binds
    fun bindsMovieListResponseToMovieModelMapper(movieListResponseToMovieModelDefaultMapper: MovieListResponseToMovieModelDefaultMapper): MovieListResponseToMovieModelMapper

    @Binds
    fun bindsMovieDetailsResponseToMovieDetailsModel(movieDetailsResponseToMovieDetailsModelDefaultMapper: MovieDetailsResponseToMovieDetailsModelDefaultMapper): MovieDetailsResponseToMovieDetailsModelMapper
}