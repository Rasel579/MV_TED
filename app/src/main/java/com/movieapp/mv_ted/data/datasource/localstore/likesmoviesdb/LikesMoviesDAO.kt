package com.movieapp.mv_ted.data.datasource.localstore.likesmoviesdb

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