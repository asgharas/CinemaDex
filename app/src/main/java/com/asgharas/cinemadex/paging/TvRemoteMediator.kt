package com.asgharas.cinemadex.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.asgharas.cinemadex.Keys
import com.asgharas.cinemadex.model.api.ApiService
import com.asgharas.cinemadex.model.data.Tv
import com.asgharas.cinemadex.model.data.TvRemoteKey
import com.asgharas.cinemadex.model.db.CinemaDb

@OptIn(ExperimentalPagingApi::class)
class TvRemoteMediator(
    private val apiService: ApiService,
    private val cinemaDb: CinemaDb
): RemoteMediator<Int, Tv>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Tv>): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = apiService.getDiscoverTv(Keys.apiKey(), currentPage)

            if(response.isSuccessful){
                val isPaginationEnd  = response.body()!!.total_pages == currentPage
                val prevPage = if(currentPage == 1) null else currentPage - 1
                val nextPage = if(isPaginationEnd) null else currentPage + 1
                val results = response.body()!!.results

                cinemaDb.withTransaction {
                    if(loadType == LoadType.REFRESH){
                        cinemaDb.getDB().deleteAllTvShows()
                        cinemaDb.getDB().deleteAllTvRemoteKeys()
                    }

                    cinemaDb.getDB().addTvShows(results)
                    val remoteKeys = results.map {
                        TvRemoteKey(
                            id = it.id,
                            prevPage = prevPage,
                            nextPage = nextPage
                        )
                    }
                    cinemaDb.getDB().addAllTvRemoteKeys(remoteKeys)
                }
                MediatorResult.Success(isPaginationEnd)

            }
            throw Exception("Request Failed")
        } catch (ex: Exception){
            MediatorResult.Error(ex)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Tv>
    ): TvRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                cinemaDb.getDB().getTvRemoteKey(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Tv>
    ): TvRemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let {
                cinemaDb.getDB().getTvRemoteKey(id = it.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Tv>
    ): TvRemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let {
                cinemaDb.getDB().getTvRemoteKey(id = it.id)
            }
    }
}