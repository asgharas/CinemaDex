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
class SearchViewModel @Inject constructor(private val repository: CinemaRepository): ViewModel() {

    val movieQueryResult: LiveData<List<Movie>>
    get() = repository.moviesSearch

    val tvQueryResult: LiveData<List<Tv>>
    get() = repository.tvShowsSearch

    fun searchTv(queryString: String) {
        viewModelScope.launch(Dispatchers.IO){
            repository.searchTv(queryString)
        }
    }
    fun searchMovies(queryString: String) {
        viewModelScope.launch(Dispatchers.IO){
            repository.searchMovie(queryString)
        }
    }
}