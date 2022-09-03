package com.asgharas.cinemadex.model.api

import com.asgharas.cinemadex.model.data.DiscoverMovieResponse
import com.asgharas.cinemadex.model.data.DiscoverTVResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object{
        const val BASE_URL = "https://api.themoviedb.org/4/"
    }

    @GET("discover/movie")
    suspend fun getDiscoverMovies(@Query("api_key") api_key: String): Response<DiscoverMovieResponse>

    @GET("discover/tv")
    suspend fun getDiscoverTv(@Query("api_key") api_key: String): Response<DiscoverTVResponse>
}