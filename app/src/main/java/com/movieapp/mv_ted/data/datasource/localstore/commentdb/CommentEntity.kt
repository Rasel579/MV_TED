package com.movieapp.mv_ted.data.datasource.localstore.commentdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CommentEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val movieId : String,
    val comment: String
)

