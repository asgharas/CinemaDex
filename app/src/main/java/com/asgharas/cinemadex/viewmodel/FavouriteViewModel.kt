package com.asgharas.cinemadex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asgharas.cinemadex.model.data.Movie
import com.asgharas.cinemadex.model.data.Tv
import com.asgharas.cinemadex.model.repository.CinemaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(private val cinemaRepository: CinemaRepository) :
    ViewModel() {

    val tvFavourites: LiveData<List<Tv>>
        get() = cinemaRepository.tvFavourites

    val movieFavourites: LiveData<List<Movie>>
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

    fun addTvFavourite(tv: Tv) {
        viewModelScope.launch(Dispatchers.IO) {
            cinemaRepository.addTvFavourite(tv)
        }
    }

    fun addMovieFavourite(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            cinemaRepository.addMovieFavourite(movie)
        }
    }

    fun removeTvFavourite(tv: Tv) {
        viewModelScope.launch(Dispatchers.IO) {
            cinemaRepository.removeTvFavourite(tv)
        }
    }

    fun removeMovieFavourite(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            cinemaRepository.removeMovieFavourite(movie)
        }
    }
}