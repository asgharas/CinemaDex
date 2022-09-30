package com.asgharas.cinemadex.model.db

import androidx.paging.PagingSource
import androidx.room.*
import com.asgharas.cinemadex.model.data.*

@Dao
interface CinemaDao {

    //Paging
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTvShows(tvShows: List<Tv>)

    @Query("SELECT * FROM Tv")
    fun getTvShows(): PagingSource<Int, Tv>

    @Query("SELECT * FROM Movie")
    fun getMovies(): PagingSource<Int, Movie>

    @Query("DELETE FROM Tv")
    suspend fun deleteAllTvShows()

    @Query("DELETE FROM Movie")
    suspend fun deleteAllMovies()

    //Paging - Remote Keys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllMovieRemoteKeys(keys: List<MovieRemoteKey>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllTvRemoteKeys(keys: List<TvRemoteKey>)

    @Query("SELECT * FROM MovieRemoteKey WHERE id =:id")
    suspend fun getMovieRemoteKey(id: Int): MovieRemoteKey

    @Query("SELECT * FROM TvRemoteKey WHERE id =:id")
    suspend fun getTvRemoteKey(id: Int): TvRemoteKey

    @Query("DELETE FROM MovieRemoteKey")
    suspend fun deleteAllMovieRemoteKeys()

    @Query("DELETE FROM TvRemoteKey")
    suspend fun deleteAllTvRemoteKeys()


    // Favourites
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavMovie(favMovie: FavMovie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavTv(favTv: FavTv)

    @Query("SELECT * FROM FavTv")
    suspend fun getFavTvShows(): List<FavTv>

    @Query("SELECT * FROM FavMovie")
    suspend fun getFavMovies(): List<FavMovie>

    @Delete
    suspend fun deleteFavTv(favTv: FavTv)

    @Delete
    suspend fun deleteFavMovie(favMovie: FavMovie)

}