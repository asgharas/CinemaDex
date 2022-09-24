package com.asgharas.cinemadex.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.asgharas.cinemadex.Keys
import com.asgharas.cinemadex.model.api.ApiService
import com.asgharas.cinemadex.model.data.DiscoverMovieResponse
import com.asgharas.cinemadex.model.data.DiscoverTVResponse
import com.asgharas.cinemadex.model.data.Movie
import com.asgharas.cinemadex.model.data.Tv
import com.asgharas.cinemadex.model.db.CinemaDao
import com.asgharas.cinemadex.paging.MoviePagingSource
import com.asgharas.cinemadex.paging.TvPagingSource
import com.asgharas.cinemadex.utils.network.NetworkResult
import retrofit2.Response
import javax.inject.Inject

class CinemaRepository @Inject constructor(
    private val apiService: ApiService,
    private val cinemaDao: CinemaDao
) {

    private val moviesLiveData = MutableLiveData<NetworkResult<DiscoverMovieResponse>>()
    private val tvLiveData = MutableLiveData<NetworkResult<DiscoverTVResponse>>()

    private val moviesSearchLiveData = MutableLiveData<NetworkResult<DiscoverMovieResponse>>()
    private val tvSearchLiveData = MutableLiveData<NetworkResult<DiscoverTVResponse>>()

    private val movieFavouriteData = MutableLiveData<List<Movie>>()
    private val tvFavouriteData = MutableLiveData<List<Tv>>()

//    val movies: LiveData<NetworkResult<DiscoverMovieResponse>>
//        get() = moviesLiveData
//    val tvShows: LiveData<NetworkResult<DiscoverTVResponse>>
//        get() = tvLiveData

    val moviesSearch: LiveData<NetworkResult<DiscoverMovieResponse>>
        get() = moviesSearchLiveData
    val tvShowsSearch: LiveData<NetworkResult<DiscoverTVResponse>>
        get() = tvSearchLiveData

    val movieFavourites: LiveData<List<Movie>>
        get() = movieFavouriteData

    val tvFavourites: LiveData<List<Tv>>
        get() = tvFavouriteData

    fun getDiscoverMovies() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        pagingSourceFactory = { MoviePagingSource(apiService) }
    ).liveData

 fun getDiscoverTv() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        pagingSourceFactory = { TvPagingSource(apiService) }
    ).liveData


//    private fun handleMovieResponse(
//        response: Response<DiscoverMovieResponse>
//    ) {
//        if (response.isSuccessful && response.body() != null) {
//            if (moviesLiveData.value?.data?.results?.isNotEmpty() == true) {
//                Log.d("TESTRR", "handleMovieResponse: not empty")
//                moviesLiveData.postValue(NetworkResult.Success(DiscoverMovieResponse(results = moviesLiveData.value?.data?.results!! + response.body()!!.results)))
//            } else {
//                moviesLiveData.postValue(NetworkResult.Success(response.body()!!))
//                Log.d("TESTRR", "handleMovieResponse: empty")
//            }
//
//        } else if (response.errorBody() != null) {
//            moviesLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
//        } else {
//            moviesLiveData.postValue(NetworkResult.Loading())
//        }
//    }
//
//    private fun handleTvResponse(
//        response: Response<DiscoverTVResponse>
//    ) {
//        if (response.isSuccessful && response.body() != null) {
//            if (tvLiveData.value?.data?.results?.isNotEmpty() == true) {
//                tvLiveData.postValue(NetworkResult.Success(DiscoverTVResponse(results = tvLiveData.value?.data?.results!! + response.body()!!.results)))
//            } else tvLiveData.postValue(NetworkResult.Success(response.body()!!))
//
//        } else if (response.errorBody() != null) {
//            tvLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
//        } else {
//            tvLiveData.postValue(NetworkResult.Loading())
//        }
//    }
    private fun handleMovieSearchResponse(
        response: Response<DiscoverMovieResponse>
    ) {
        if (response.isSuccessful && response.body() != null) {
            if (moviesSearchLiveData.value?.data?.results?.isNotEmpty() == true) {
                moviesSearchLiveData.postValue(NetworkResult.Success(DiscoverMovieResponse(results = moviesSearchLiveData.value?.data?.results!! + response.body()!!.results)))
            } else moviesSearchLiveData.postValue(NetworkResult.Success(response.body()!!))

        } else if (response.errorBody() != null) {
            moviesSearchLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        } else {
            moviesSearchLiveData.postValue(NetworkResult.Loading())
        }
    }

    private fun handleTvSearchResponse(
        response: Response<DiscoverTVResponse>
    ) {
        if (response.isSuccessful && response.body() != null) {
            if (tvSearchLiveData.value?.data?.results?.isNotEmpty() == true) {
                tvSearchLiveData.postValue(NetworkResult.Success(DiscoverTVResponse(results = tvSearchLiveData.value?.data?.results!! + response.body()!!.results)))
            } else tvSearchLiveData.postValue(NetworkResult.Success(response.body()!!))

        } else if (response.errorBody() != null) {
            tvSearchLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        } else {
            tvSearchLiveData.postValue(NetworkResult.Loading())
        }
    }

    suspend fun searchTv(queryString: String) {
        val response = apiService.searchTv(Keys.apiKey(), queryString)
        handleTvSearchResponse(response)
    }

    suspend fun searchMovie(queryString: String) {
        val response = apiService.searchMovie(Keys.apiKey(), queryString)
        handleMovieSearchResponse(response)
    }

    suspend fun addMovieFavourite(movie: Movie) {
        cinemaDao.insertMovie(movie)
        getMovieFavourites()
    }

    suspend fun getMovieFavourites() {
        val result = cinemaDao.getMovies()
        movieFavouriteData.postValue(result)
    }

    suspend fun addTvFavourite(tv: Tv) {
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