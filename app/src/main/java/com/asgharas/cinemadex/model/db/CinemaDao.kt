package com.asgharas.cinemadex.model.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.asgharas.cinemadex.model.data.Movie
import com.asgharas.cinemadex.model.data.Tv

@Dao
interface CinemaDao {

    @Insert
    suspend fun insertMovie(movie: Movie)

    @Insert
    suspend fun insertTv(tvShow: Tv)

    @Query("SELECT * FROM Tv")
    suspend fun getTvShows(): List<Tv>

    @Query("SELECT * FROM Movie")
    suspend fun getMovies(): List<Movie>

    @Delete
    suspend fun deleteTvFavourite(tv: Tv)

    @Delete
    suspend fun deleteMovieFavourite(movie: Movie)

}