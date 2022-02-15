package com.movieapp.mv_ted.data.datasource.localstore.likesmoviesdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LikesMoviesEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val title: String?,
    val image: String?,
    val date: String?,
    val description: String?
)
