package com.example.imovie

import com.example.imovie.service.Response
import retrofit2.Call
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
    fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: String
    ): Call<Response>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object TheMovieDbApi {
    val retrofitService : TheMovieDbApiService by lazy { retrofit.create(TheMovieDbApiService::class.java) }
}