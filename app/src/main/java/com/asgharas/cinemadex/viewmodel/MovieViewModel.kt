package com.asgharas.cinemadex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asgharas.cinemadex.model.data.Movie
import com.asgharas.cinemadex.model.repository.CinemaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: CinemaRepository) : ViewModel() {

    init {
        viewModelScope.launch {
            if(repository.movies.value == null || repository.tvShows.value!!.isEmpty()){
                repository.getDiscoverMovies()
            }
        }
    }

    val moviesList: LiveData<List<Movie>>
    get() = repository.movies
}