package com.movieapp.mv_ted.models.data.model.database.liked_movie_db

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
