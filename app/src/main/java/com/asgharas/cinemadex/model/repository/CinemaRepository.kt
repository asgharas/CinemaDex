package com.asgharas.cinemadex.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.asgharas.cinemadex.Keys
import com.asgharas.cinemadex.model.api.ApiService
import com.asgharas.cinemadex.model.data.DiscoverMovieResponse
import com.asgharas.cinemadex.model.data.DiscoverTVResponse
import com.asgharas.cinemadex.model.data.FavMovie
import com.asgharas.cinemadex.model.data.FavTv
import com.asgharas.cinemadex.model.db.CinemaDb
import com.asgharas.cinemadex.paging.MoviePagingSource
import com.asgharas.cinemadex.paging.TvPagingSource
import com.asgharas.cinemadex.utils.network.NetworkResult
import retrofit2.Response
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CinemaRepository @Inject constructor(
    private val apiService: ApiService,
    private val cinemaDb: CinemaDb
) {

    private val moviesSearchLiveData = MutableLiveData<NetworkResult<DiscoverMovieResponse>>()
    private val tvSearchLiveData = MutableLiveData<NetworkResult<DiscoverTVResponse>>()

    private val movieFavouriteData = MutableLiveData<List<FavMovie>>()
    private val tvFavouriteData = MutableLiveData<List<FavTv>>()

    val moviesSearch: LiveData<NetworkResult<DiscoverMovieResponse>>
        get() = moviesSearchLiveData
    val tvShowsSearch: LiveData<NetworkResult<DiscoverTVResponse>>
        get() = tvSearchLiveData

    val movieFavourites: LiveData<List<FavMovie>>
        get() = movieFavouriteData

    val tvFavourites: LiveData<List<FavTv>>
        get() = tvFavouriteData

    fun getDiscoverMovies() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
//        remoteMediator = MovieRemoteMediator(apiService, cinemaDb),
//        pagingSourceFactory = { cinemaDb.getDB().getMovies() }
        pagingSourceFactory = { MoviePagingSource(apiService) }
    ).liveData

    fun getDiscoverTv() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
//     remoteMediator = TvRemoteMediator(apiService, cinemaDb),
//     pagingSourceFactory = { cinemaDb.getDB().getTvShows() }
        pagingSourceFactory = { TvPagingSource(apiService) }
    ).liveData


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

    suspend fun addMovieFavourite(favMovie: FavMovie) {
        cinemaDb.getDB().insertFavMovie(favMovie)
        getMovieFavourites()
    }

    suspend fun getMovieFavourites() {
        val result = cinemaDb.getDB().getFavMovies()
        movieFavouriteData.postValue(result)
    }

    suspend fun addTvFavourite(favTv: FavTv) {
        cinemaDb.getDB().insertFavTv(favTv)
        getTvFavourites()
    }

    suspend fun getTvFavourites() {
        val result = cinemaDb.getDB().getFavTvShows()
        tvFavouriteData.postValue(result)
    }

    suspend fun removeTvFavourite(favTv: FavTv) {
        cinemaDb.getDB().deleteFavTv(favTv)
        getTvFavourites()
    }

    suspend fun removeMovieFavourite(favMovie: FavMovie) {
        cinemaDb.getDB().deleteFavMovie(favMovie)
        getMovieFavourites()
    }
}