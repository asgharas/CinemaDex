package com.asgharas.cinemadex.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.asgharas.cinemadex.model.api.ApiService
import com.asgharas.cinemadex.model.data.Movie
import com.asgharas.cinemadex.model.data.Tv
import com.asgharas.cinemadex.model.db.CinemaDao
import com.asgharas.cinemadex.other.API_KEY
import javax.inject.Inject

class CinemaRepository @Inject constructor(
    private val apiService: ApiService,
    private val cinemaDao: CinemaDao
) {
    private var page: Int = 1

    private val moviesLiveData = MutableLiveData<List<Movie>>()
    private val tvLiveData = MutableLiveData<List<Tv>>()

    private val moviesSearchLiveData = MutableLiveData<List<Movie>>()
    private val tvSearchLiveData = MutableLiveData<List<Tv>>()

    private val movieFavouriteData = MutableLiveData<List<Movie>>()
    private val tvFavouriteData = MutableLiveData<List<Tv>>()

    val movies: LiveData<List<Movie>>
        get() = moviesLiveData
    val tvShows: LiveData<List<Tv>>
        get() = tvLiveData

    val moviesSearch: LiveData<List<Movie>>
        get() = moviesSearchLiveData
    val tvShowsSearch: LiveData<List<Tv>>
        get() = tvSearchLiveData

    val movieFavourites : LiveData<List<Movie>>
    get() = movieFavouriteData

    val tvFavourites : LiveData<List<Tv>>
    get() = tvFavouriteData

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

    suspend fun searchTv(queryString: String) {
        val result = apiService.searchTv(API_KEY, queryString)
        if (result.body() != null && result.isSuccessful) {
            tvSearchLiveData.postValue(result.body()!!.results)
        }
    }

    suspend fun searchMovie(queryString: String) {
        val result = apiService.searchMovie(API_KEY, queryString)
        if (result.body() != null && result.isSuccessful) {
            moviesSearchLiveData.postValue(result.body()!!.results)
        }
    }

    suspend fun addMovieFavourite(movie: Movie){
        cinemaDao.insertMovie(movie)
        getMovieFavourites()
    }

    suspend fun getMovieFavourites() {
        val result = cinemaDao.getMovies()
        movieFavouriteData.postValue(result)
    }

    suspend fun addTvFavourite(tv: Tv){
        cinemaDao.insertTv(tv)
        getTvFavourites()
    }

    suspend fun getTvFavourites() {
        val result = cinemaDao.getTvShows()
        tvFavouriteData.postValue(result)
    }

    suspend fun removeTvFavourite(tv: Tv) {
        cinemaDao.deleteTvFavourite(tv)
        getTvFavourites()
    }

    suspend fun removeMovieFavourite(movie: Movie) {
        cinemaDao.deleteMovieFavourite(movie)
        getMovieFavourites()
    }
}