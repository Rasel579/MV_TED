package com.movieapp.mv_ted.models.data.model

import com.movieapp.mv_ted.models.data.model.rest.rest_mdbApi.Film
import com.movieapp.mv_ted.models.data.model.rest.rest_mdbApi.MovieDTO
import com.movieapp.mv_ted.models.data.model.rest.rest_mdbApi.MovieResultDTO
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