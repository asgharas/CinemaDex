package com.asgharas.cinemadex.model.data

data class DiscoverMovieResponse(
    val page: Int = 1,
    val results: List<Movie>,
    val total_pages: Int = 1,
    val total_results: Int = 1
)