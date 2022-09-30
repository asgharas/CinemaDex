package com.asgharas.cinemadex.model.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Tv")
data class Tv(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val backdrop_path: String,
    val first_air_date: String,
    val name: String,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val vote_average: Double,
    val vote_count: Int
) : Parcelable {
    fun getFavTv() = FavTv(
        id,
        backdrop_path,
        first_air_date,
        name,
        original_language,
        original_name,
        overview,
        popularity,
        poster_path,
        vote_average,
        vote_count
    )
}