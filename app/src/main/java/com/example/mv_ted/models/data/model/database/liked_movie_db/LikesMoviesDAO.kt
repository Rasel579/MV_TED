package com.example.mv_ted.models.data.model.database.liked_movie_db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LikesMoviesDAO {
    @Query("Select * from LikesMoviesEntity")
    fun getLikesMovies(): List<LikesMoviesEntity>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLike(likesMoviesEntity: LikesMoviesEntity)
}