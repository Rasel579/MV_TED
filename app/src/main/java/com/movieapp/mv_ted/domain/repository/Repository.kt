package com.movieapp.mv_ted.domain.repository

import com.movieapp.mv_ted.domain.models.Comment
import com.movieapp.mv_ted.domain.models.Movie
import com.movieapp.mv_ted.domain.models.response.Film
import com.movieapp.mv_ted.domain.models.response.MovieDTO
import com.movieapp.mv_ted.domain.models.response.MovieResultDTO
import retrofit2.Callback
import java.net.URL

interface Repository {
    fun getDataFromServer(uri: URL) : MutableList<MovieResultDTO>?
    fun getDataFromServerRetrofit(callback : Callback<MovieDTO>)
    fun getDataFromServerRetrofitUpcoming(callback : Callback<MovieDTO>)
    fun getDataFromServerAboutFilm(callback: Callback<Film>, movieId: String)
    fun getDataFromLocalStorage() : MutableList<Movie>
    fun getDataFromMovieAPI() : MutableList<Movie>
    fun getHistoryComments(movieId : String) : List<Comment>
    fun saveEntity(comment: Comment)
    fun getAllLikesMovies() : List<Movie>
    fun saveLikes(movie: Movie)
}