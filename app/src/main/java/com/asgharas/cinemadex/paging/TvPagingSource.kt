package com.asgharas.cinemadex.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.asgharas.cinemadex.Keys
import com.asgharas.cinemadex.model.api.ApiService
import com.asgharas.cinemadex.model.data.Tv

class TvPagingSource(private val apiService: ApiService): PagingSource<Int, Tv>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Tv> {
        return try {
            val position = params.key ?: 1
            val response = apiService.getDiscoverTv(Keys.apiKey(), position)
            LoadResult.Page(
                data = response.body()!!.results,
                prevKey = if(position == 1) null else position - 1,
                nextKey = if(position == response.body()!!.total_pages) null else position + 1
            )
        }catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Tv>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}