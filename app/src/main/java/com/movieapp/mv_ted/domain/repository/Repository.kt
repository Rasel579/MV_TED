package com.movieapp.mv_ted.domain.repository

import com.movieapp.mv_ted.domain.models.Comment
import com.movieapp.mv_ted.domain.models.Movie
import com.movieapp.mv_ted.domain.models.response.credits.ActorsResponse
import com.movieapp.mv_ted.domain.models.response.movie.Film
import com.movieapp.mv_ted.domain.models.response.movie.MovieDTO
import com.movieapp.mv_ted.domain.models.response.movie.MovieResponse
import retrofit2.Callback

interface Repository {
    suspend fun getDataFromServerRetrofit(): MovieDTO
    suspend fun getDataNextList(page: Int): MovieDTO
    fun getDataFromServerAboutFilm(callback: Callback<Film>, movieId: String)
    suspend fun getHistoryComments(movieId : String) : List<Comment>
    fun saveEntity(comment: Comment)
    fun getAllLikesMovies() : List<Movie>
    fun saveLikes(movie: Movie)
    suspend fun getMovieById(movieId: String) : MovieResponse
    suspend fun getCredits(movieId: String) : ActorsResponse
}