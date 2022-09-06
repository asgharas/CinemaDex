package com.asgharas.cinemadex.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.asgharas.cinemadex.model.data.Movie
import com.asgharas.cinemadex.model.data.Tv

@Database(entities = [Tv::class, Movie::class], version = 1)
abstract class CinemaDb: RoomDatabase() {

    abstract fun getDB(): CinemaDao


}