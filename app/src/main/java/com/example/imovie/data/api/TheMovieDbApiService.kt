package com.example.imovie.data.api

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.themoviedb.org/3/"

private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

interface TheMovieDbApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
            @Query("api_key") apiKey: String,
            @Query("language") language: String
    ): Response<MovieListResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
            @Query("api_key") apiKey: String,
            @Query("language") language: String
    ): Response<MovieListResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
            @Query("api_key") apiKey: String,
            @Query("language") language: String
    ): Response<MovieListResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
            @Query("api_key") apiKey: String,
            @Query("language") language: String
    ): Response<MovieListResponse>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object TheMovieDbApi {
    val retrofitService: TheMovieDbApiService by lazy { retrofit.create(TheMovieDbApiService::class.java) }
}