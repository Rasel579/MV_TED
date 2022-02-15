package com.movieapp.mv_ted.models.data.model.database.comment_db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.movieapp.mv_ted.App

@Database(entities = [CommentEntity::class], version = 1, exportSchema = false)
abstract class CommentDataBase : RoomDatabase() {
    abstract fun commentDao() : CommentDAO

    companion object {
       private const val DB_NAME = "add_database.db"
       val db : CommentDataBase by lazy {
           Room.databaseBuilder(
               App.appInstance,
               CommentDataBase :: class.java,
               DB_NAME
           ).build()
       }
    }
}