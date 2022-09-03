package com.asgharas.cinemadex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asgharas.cinemadex.model.data.Tv
import com.asgharas.cinemadex.model.repository.CinemaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvViewModel @Inject constructor(private val repository: CinemaRepository) : ViewModel() {

    init {
        viewModelScope.launch {
            if(repository.tvShows.value == null || repository.tvShows.value!!.isEmpty()){
                repository.getDiscoverTv()
            }
        }
    }

    val tvShowsList: LiveData<List<Tv>>
        get() = repository.tvShows
}