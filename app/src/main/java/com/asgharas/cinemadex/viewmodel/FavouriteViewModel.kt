package com.asgharas.cinemadex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asgharas.cinemadex.model.data.FavMovie
import com.asgharas.cinemadex.model.data.FavTv
import com.asgharas.cinemadex.model.repository.CinemaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(private val cinemaRepository: CinemaRepository) :
    ViewModel() {

    val tvFavourites: LiveData<List<FavTv>>
        get() = cinemaRepository.tvFavourites

    val movieFavourites: LiveData<List<FavMovie>>
        get() = cinemaRepository.movieFavourites


    fun getTvFavourites() {
        viewModelScope.launch(Dispatchers.IO) {
           cinemaRepository.getTvFavourites()
        }
    }

    fun getMovieFavourites() {
        viewModelScope.launch(Dispatchers.IO) {
            cinemaRepository.getMovieFavourites()
        }
    }

    fun addTvFavourite(favTv: FavTv) {
        viewModelScope.launch(Dispatchers.IO) {
            cinemaRepository.addTvFavourite(favTv)
        }
    }

    fun addMovieFavourite(favMovie: FavMovie) {
        viewModelScope.launch(Dispatchers.IO) {
            cinemaRepository.addMovieFavourite(favMovie)
        }
    }

    fun removeTvFavourite(favTv: FavTv) {
        viewModelScope.launch(Dispatchers.IO) {
            cinemaRepository.removeTvFavourite(favTv)
        }
    }

    fun removeMovieFavourite(favMovie: FavMovie) {
        viewModelScope.launch(Dispatchers.IO) {
            cinemaRepository.removeMovieFavourite(favMovie)
        }
    }
}