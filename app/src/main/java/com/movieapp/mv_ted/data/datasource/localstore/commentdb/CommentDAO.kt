package com.movieapp.mv_ted.data.datasource.localstore.commentdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CommentDAO {
    @Query("Select * from CommentEntity")
    fun getAll(): List<CommentEntity>
    @Query("Select * from CommentEntity WHERE movieId LIKE :movieId")
    fun getDataByMovie(movieId : String) : List<CommentEntity>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: CommentEntity)
}