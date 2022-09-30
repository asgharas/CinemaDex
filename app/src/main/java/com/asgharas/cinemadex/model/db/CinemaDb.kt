package com.asgharas.cinemadex.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.asgharas.cinemadex.model.data.*

@Database(
    entities = [Tv::class, Movie::class, FavTv::class, FavMovie::class, MovieRemoteKey::class, TvRemoteKey::class],
    version = 3
)
abstract class CinemaDb : RoomDatabase() {

    abstract fun getDB(): CinemaDao


}