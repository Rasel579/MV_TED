package com.movieapp.mv_ted.data.repository

import com.movieapp.mv_ted.domain.models.Comment
import com.movieapp.mv_ted.domain.models.Movie
import com.movieapp.mv_ted.domain.repository.Repository
import com.movieapp.mv_ted.data.datasource.localstore.commentdb.CommentDataBase
import com.movieapp.mv_ted.data.datasource.localstore.commentdb.CommentEntity
import com.movieapp.mv_ted.data.datasource.localstore.likesmoviesdb.LikesMoviesDatabase
import com.movieapp.mv_ted.data.datasource.localstore.likesmoviesdb.LikesMoviesEntity
import com.movieapp.mv_ted.models.data.model.getDataMovie
import com.movieapp.mv_ted.domain.models.response.Film
import com.movieapp.mv_ted.domain.models.response.MovieDTO
import com.movieapp.mv_ted.domain.models.response.MovieResponse
import com.movieapp.mv_ted.data.datasource.cloudsource.rest_mdbApi.MoviesLoader
import com.movieapp.mv_ted.data.datasource.cloudsource.api.BackendRepo
import retrofit2.Callback
import java.net.URL

class RepositoryImpl : Repository {
    override fun getDataFromServer(uri: URL): MutableList<MovieResponse>?  = MoviesLoader.loadMovies(uri)
    override fun getDataFromServerRetrofit(callback: Callback<MovieDTO>) {
        BackendRepo.api.getMoviesList().enqueue(callback)
    }

    override fun getDataFromServerRetrofitUpcoming(callback: Callback<MovieDTO>) {
       BackendRepo.api.getMoviesListUpcoming().enqueue(callback)
    }

    override fun getDataFromServerAboutFilm(callback: Callback<Film>, movieId: String) {
        BackendRepo.api.getMovieByIdCallBack(movieId).enqueue(callback)
    }

    override fun getDataFromLocalStorage() = getDataMovie()
    override fun getDataFromMovieAPI() = getDataMovie()
    override fun getHistoryComments(movieId: String): List<Comment> =  convertCommentEntity(
        CommentDataBase.db.commentDao().getDataByMovie(movieId))


    private fun convertCommentEntity(getDataByMovie: List<CommentEntity>) : List<Comment> =
               getDataByMovie.map {
                    Comment(it.id, it.movieId, it.comment)
               }
    override fun saveEntity(comment: Comment){
        CommentDataBase.db.commentDao().insert(convertCommentToCommentEntity(comment))
    }

    private fun convertCommentToCommentEntity(comment: Comment): CommentEntity =
        CommentEntity(0, comment.movieId, comment.comment)

    override fun getAllLikesMovies(): List<Movie> =
        convertLikesEntityToMovie(LikesMoviesDatabase.db.likesMoviesDao().getLikesMovies())


    private fun convertLikesEntityToMovie(likesMovies: List<LikesMoviesEntity>) =
        likesMovies.map {
            Movie(it.title, it.date, it.image, it.description)
        }



    override fun saveLikes(movie: Movie) {
       LikesMoviesDatabase.db.likesMoviesDao().insertLike(convertMovieToLikesEntity(movie))
    }

    override suspend fun getMovieById(movieId: String): MovieResponse = BackendRepo.api.getMovieById(movieId = movieId)

    private fun convertMovieToLikesEntity(movie: Movie): LikesMoviesEntity =
        LikesMoviesEntity(0,movie.title, movie.image, movie.date, movie.description)

}



