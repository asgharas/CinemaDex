package com.asgharas.cinemadex.view.listeners

import com.asgharas.cinemadex.model.data.Movie

interface MovieClickListener {
    fun handleMovieClick(movie: Movie)
}