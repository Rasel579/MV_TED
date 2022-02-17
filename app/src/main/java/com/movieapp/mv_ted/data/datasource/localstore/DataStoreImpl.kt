package com.movieapp.mv_ted.data.datasource.localstore

import com.movieapp.mv_ted.data.datasource.localstore.commentdb.CommentDAO
import com.movieapp.mv_ted.data.datasource.localstore.commentdb.CommentEntity
import com.movieapp.mv_ted.data.datasource.localstore.likesmoviesdb.LikesMoviesDAO
import com.movieapp.mv_ted.data.datasource.localstore.likesmoviesdb.LikesMoviesEntity

class DataStoreImpl(
    private val commentDb: CommentDAO,
    private val likesDb: LikesMoviesDAO
) : DataStore {
    override fun getAll(): List<CommentEntity> = commentDb.getAll()

    override fun getDataByMovie(movieId: String): List<CommentEntity> =
        commentDb.getDataByMovie(movieId)

    override fun insert(entity: CommentEntity) = commentDb.insert(entity)

    override fun getLikesMovies(): List<LikesMoviesEntity> = likesDb.getLikesMovies()

    override fun insertLike(likesMoviesEntity: LikesMoviesEntity) =
        likesDb.insertLike(likesMoviesEntity)
}