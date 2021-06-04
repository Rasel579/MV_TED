package com.example.mv_ted.models.data.model

import com.example.mv_ted.models.data.model.database.comment_db.CommentDataBase
import com.example.mv_ted.models.data.model.database.comment_db.CommentEntity
import com.example.mv_ted.models.data.model.database.liked_movie_db.LikesMoviesDatabase
import com.example.mv_ted.models.data.model.database.liked_movie_db.LikesMoviesEntity
import com.example.mv_ted.models.data.model.rest.rest_mdbApi.Film
import com.example.mv_ted.models.data.model.rest.rest_mdbApi.MovieDTO
import com.example.mv_ted.models.data.model.rest.rest_mdbApi.MovieResultDTO
import com.example.mv_ted.models.data.model.rest.rest_mdbApi.MoviesLoader
import com.example.mv_ted.models.data.model.rest.retrofit_rest.BackendRepo
import retrofit2.Callback
import java.net.URL

class RepositoryImpl : Repository {
    override fun getDataFromServer(uri: URL): MutableList<MovieResultDTO>?  = MoviesLoader.loadMovies(uri)
    override fun getDataFromServerRetrofit(callback: Callback<MovieDTO>) {
        BackendRepo.api.getMoviesList().enqueue(callback)
    }

    override fun getDataFromServerRetrofitUpcoming(callback: Callback<MovieDTO>) {
       BackendRepo.api.getMoviesListUpcoming().enqueue(callback)
    }

    override fun getDataFromServerAboutFilm(callback: Callback<Film>, movieId: String) {
        BackendRepo.api.getMovieById(movieId).enqueue(callback)
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

    private fun convertMovieToLikesEntity(movie: Movie): LikesMoviesEntity =
        LikesMoviesEntity(0,movie.title, movie.image, movie.date, movie.description)

}



