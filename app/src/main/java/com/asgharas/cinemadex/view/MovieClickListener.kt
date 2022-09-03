package com.asgharas.cinemadex.view

import com.asgharas.cinemadex.model.data.Movie

interface MovieClickListener {
    fun handleMovieClick(movie: Movie)
}