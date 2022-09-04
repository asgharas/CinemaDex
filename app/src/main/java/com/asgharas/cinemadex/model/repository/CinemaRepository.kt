package com.asgharas.cinemadex.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.asgharas.cinemadex.model.api.ApiService
import com.asgharas.cinemadex.model.data.Movie
import com.asgharas.cinemadex.model.data.Tv
import com.asgharas.cinemadex.other.API_KEY
import javax.inject.Inject

class CinemaRepository @Inject constructor(private val apiService: ApiService) {
    private var page: Int = 1

    private val moviesLiveData = MutableLiveData<List<Movie>>()
    private val tvLiveData = MutableLiveData<List<Tv>>()

    private val moviesSearchLiveData = MutableLiveData<List<Movie>>()
    private val tvSearchLiveData = MutableLiveData<List<Tv>>()

    val movies: LiveData<List<Movie>>
        get() = moviesLiveData
    val tvShows: LiveData<List<Tv>>
        get() = tvLiveData

    val moviesSearch: LiveData<List<Movie>>
        get() = moviesSearchLiveData
    val tvShowsSearch: LiveData<List<Tv>>
        get() = tvSearchLiveData

    suspend fun getDiscoverMovies() {
        val result = apiService.getDiscoverMovies(API_KEY, page)
        if (result.body() != null && result.isSuccessful) {
            moviesLiveData.postValue(result.body()!!.results.toMutableList())
        }
    }

    suspend fun getDiscoverTv() {
        val result = apiService.getDiscoverTv(API_KEY, page)
        if (result.body() != null && result.isSuccessful) {
            tvLiveData.postValue(result.body()!!.results)
        }
    }

    suspend fun loadNextMoviePage() {
        val result = apiService.getDiscoverMovies(API_KEY, ++page)
        if (result.body() != null && result.isSuccessful && moviesLiveData.value != null) {
            moviesLiveData.postValue(moviesLiveData.value!! + result.body()!!.results)
        }
    }

    suspend fun loadNextTvPage() {
        val result = apiService.getDiscoverTv(API_KEY, ++page)
        if (result.body() != null && result.isSuccessful && tvLiveData.value != null) {
            tvLiveData.postValue(tvLiveData.value!! + result.body()!!.results)
        }
    }

    suspend fun searchTv(queryString: String){
        val result = apiService.searchTv(API_KEY, queryString)
        if(result.body() != null && result.isSuccessful) {
            tvSearchLiveData.postValue(result.body()!!.results)
        }
    }

    suspend fun searchMovie(queryString: String){
        val result = apiService.searchMovie(API_KEY, queryString)
        if(result.body() != null && result.isSuccessful) {
            Log.d("TESTING ", "searchMovie: ${result.body()!!.results[0]}")
            moviesSearchLiveData.postValue(result.body()!!.results)
        }
    }
}