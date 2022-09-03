package com.asgharas.cinemadex.model.data

data class DiscoverTVResponse(
    val page: Int,
    val results: List<Tv>,
    val total_pages: Int,
    val total_results: Int
)