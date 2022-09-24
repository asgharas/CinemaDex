package com.asgharas.cinemadex.model.data

data class DiscoverTVResponse(
    val page: Int = 1,
    val results: List<Tv>,
    val total_pages: Int = 1,
    val total_results: Int = 1
)