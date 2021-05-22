package com.example.mv_ted.models.data.model.database.liked_movie_db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mv_ted.App

@Database(entities = [LikesMoviesEntity::class], version = 1, exportSchema = false)
abstract class LikesMoviesDatabase : RoomDatabase() {
    abstract fun likesMoviesDao() : LikesMoviesDAO
    companion object{
        private const val DB_LIKES_NAME = "db_likes.db"
        val db : LikesMoviesDatabase by lazy {
            Room.databaseBuilder(
                App.appInstance,
                LikesMoviesDatabase::class.java,
                DB_LIKES_NAME
            ).build()
        }
    }
}