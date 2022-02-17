package com.movieapp.mv_ted.data.datasource.cloudsource

import com.movieapp.mv_ted.domain.models.response.credits.ActorsResponse
import com.movieapp.mv_ted.domain.models.response.movie.Film
import com.movieapp.mv_ted.domain.models.response.movie.MovieDTO
import com.movieapp.mv_ted.domain.models.response.movie.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CloudSource{
    suspend fun getMoviesList(): MovieDTO
    suspend fun getMoviesListPagination(@Query("page") page: Int): MovieDTO
    fun getMoviesListUpcoming(): Call<MovieDTO>
    suspend fun getMovieById(@Path("movieId") movieId : String): MovieResponse
    fun getMovieByIdCallBack(@Path("movieId") movieId : String): Call<Film>
    suspend fun getCredits(@Path("movieId") movieId : String): ActorsResponse
}