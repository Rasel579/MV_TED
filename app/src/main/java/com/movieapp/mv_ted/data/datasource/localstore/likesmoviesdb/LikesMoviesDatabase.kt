package com.movieapp.mv_ted.data.datasource.localstore.likesmoviesdb

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.movieapp.mv_ted.App

@Database(entities = [LikesMoviesEntity::class], version = 3, exportSchema = false)
abstract class LikesMoviesDatabase : RoomDatabase() {
    abstract fun likesMoviesDao() : LikesMoviesDAO
    companion object{
        private const val DB_LIKES_NAME = "db_likes_ver_3.db"
        val db : LikesMoviesDatabase by lazy {
            Room.databaseBuilder(
                App.appInstance,
                LikesMoviesDatabase::class.java,
                DB_LIKES_NAME
            ).build()
        }
    }
}