package com.asgharas.cinemadex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asgharas.cinemadex.model.data.Movie
import com.asgharas.cinemadex.model.repository.CinemaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteMovieViewModel @Inject constructor(private val cinemaRepository: CinemaRepository) :
    ViewModel() {

    fun getFavourites() {
        viewModelScope.launch(Dispatchers.IO) {
            cinemaRepository.getMovieFavourites()
        }
    }

    val movieFavourites: LiveData<List<Movie>>
        get() = cinemaRepository.movieFavourites


    fun addMovieFavourite(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            cinemaRepository.addMovieFavourite(movie)
        }
    }
}