package com.movieapp.mv_ted.data.datasource.localstore
import com.movieapp.mv_ted.data.datasource.localstore.commentdb.CommentEntity
import com.movieapp.mv_ted.data.datasource.localstore.likesmoviesdb.LikesMoviesEntity

interface DataStore {
    fun getAll(): List<CommentEntity>
    fun getDataByMovie(movieId : String) : List<CommentEntity>
    fun insert(entity: CommentEntity)
    fun getLikesMovies(): List<LikesMoviesEntity>
    fun insertLike(likesMoviesEntity: LikesMoviesEntity)
}