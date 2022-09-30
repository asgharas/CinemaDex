package com.asgharas.cinemadex.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieRemoteKey(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?
)

@Entity
data class TvRemoteKey(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?
)