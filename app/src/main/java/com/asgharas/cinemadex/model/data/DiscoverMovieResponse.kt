package com.asgharas.cinemadex.model.data

data class DiscoverMovieResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)