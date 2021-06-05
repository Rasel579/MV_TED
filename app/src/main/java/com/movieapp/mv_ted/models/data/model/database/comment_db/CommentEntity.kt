package com.movieapp.mv_ted.models.data.model.database.comment_db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CommentEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val movieId : String,
    val comment: String
)

