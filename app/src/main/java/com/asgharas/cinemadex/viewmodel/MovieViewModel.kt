package com.asgharas.cinemadex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.asgharas.cinemadex.model.repository.CinemaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(repository: CinemaRepository) : ViewModel() {

    val moviesList = repository.getDiscoverMovies().cachedIn(viewModelScope)

}