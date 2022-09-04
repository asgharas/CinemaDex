package com.asgharas.cinemadex.model.api

import com.asgharas.cinemadex.model.data.DiscoverMovieResponse
import com.asgharas.cinemadex.model.data.DiscoverTVResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/4/"
    }

    @GET("discover/movie")
    suspend fun getDiscoverMovies(
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ): Response<DiscoverMovieResponse>

    @GET("discover/tv")
    suspend fun getDiscoverTv(
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ): Response<DiscoverTVResponse>

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("api_key") api_key: String,
        @Query("query") query: String
    ): Response<DiscoverMovieResponse>

    // https://api.themoviedb.org/4/search/movie?api_key=5eb0f00b364d04f15d7208f7396bfea8&query=war
    @GET("search/tv")
    suspend fun searchTv(
        @Query("api_key") api_key: String,
        @Query("query") query: String
    ): Response<DiscoverTVResponse>
}