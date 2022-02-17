package com.movieapp.mv_ted.data.repository

import com.movieapp.mv_ted.data.datasource.cloudsource.CloudSource
import com.movieapp.mv_ted.data.datasource.cloudsource.CloudSourceImpl
import com.movieapp.mv_ted.domain.models.Comment
import com.movieapp.mv_ted.domain.models.Movie
import com.movieapp.mv_ted.domain.repository.Repository
import com.movieapp.mv_ted.data.datasource.localstore.commentdb.CommentDataBase
import com.movieapp.mv_ted.data.datasource.localstore.commentdb.CommentEntity
import com.movieapp.mv_ted.data.datasource.localstore.likesmoviesdb.LikesMoviesDatabase
import com.movieapp.mv_ted.data.datasource.localstore.likesmoviesdb.LikesMoviesEntity
import com.movieapp.mv_ted.models.data.model.getDataMovie
import com.movieapp.mv_ted.domain.models.response.movie.Film
import com.movieapp.mv_ted.domain.models.response.movie.MovieDTO
import com.movieapp.mv_ted.domain.models.response.movie.MovieResponse
import com.movieapp.mv_ted.data.datasource.cloudsource.rest_mdbApi.MoviesLoader
import com.movieapp.mv_ted.data.datasource.cloudsource.api.BackendRepo
import com.movieapp.mv_ted.data.datasource.localstore.DataStore
import com.movieapp.mv_ted.data.datasource.localstore.DataStoreImpl
import com.movieapp.mv_ted.domain.models.response.credits.ActorsResponse
import retrofit2.Callback
import java.net.URL

class RepositoryImpl(
    private val cloudSource: CloudSource = CloudSourceImpl(),
    private val dataStore: DataStore = DataStoreImpl()
) : Repository {
    override suspend fun getDataFromServerRetrofit(): MovieDTO = cloudSource.getMoviesList()
    override suspend fun getDataNextList(page: Int): MovieDTO =
        cloudSource.getMoviesListPagination(page)

    override fun getDataFromServerAboutFilm(callback: Callback<Film>, movieId: String) {
        cloudSource.getMovieByIdCallBack(movieId).enqueue(callback)
    }

    override suspend fun getHistoryComments(movieId: String): List<Comment> = convertCommentEntity(
        dataStore.getDataByMovie(movieId)
    )

    private fun convertCommentEntity(getDataByMovie: List<CommentEntity>): List<Comment> =
        getDataByMovie.map { commentEntity ->
            Comment(
                commentEntity.id,
                commentEntity.movieId,
                commentEntity.comment
            )
        }

    override fun saveEntity(comment: Comment) {
        dataStore.insert(convertCommentToCommentEntity(comment))
    }

    private fun convertCommentToCommentEntity(comment: Comment): CommentEntity =
        CommentEntity(0, comment.movieId, comment.comment)

    override fun getAllLikesMovies(): List<Movie> =
        convertLikesEntityToMovie(dataStore.getLikesMovies())


    private fun convertLikesEntityToMovie(likesMovies: List<LikesMoviesEntity>) =
        likesMovies.map {
            Movie(it.title, it.date, it.image, it.description)
        }


    override fun saveLikes(movie: Movie) =
        dataStore.insertLike(convertMovieToLikesEntity(movie))


    override suspend fun getMovieById(movieId: String): MovieResponse =
        cloudSource.getMovieById(movieId)

    override suspend fun getCredits(movieId: String): ActorsResponse =
        cloudSource.getCredits(movieId)

    private fun convertMovieToLikesEntity(movie: Movie): LikesMoviesEntity =
        LikesMoviesEntity(0, movie.title, movie.image, movie.date, movie.description)

}



