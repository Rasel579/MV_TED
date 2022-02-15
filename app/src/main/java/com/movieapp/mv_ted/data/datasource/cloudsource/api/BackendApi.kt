package com.movieapp.mv_ted.data.datasource.cloudsource.api

import com.movieapp.mv_ted.domain.models.response.Film
import com.movieapp.mv_ted.domain.models.response.MovieDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BackendApi {
  @GET("movie/popular")
  fun getMoviesList(): Call<MovieDTO>
  @GET("movie/upcoming")
  fun getMoviesListUpcoming(): Call<MovieDTO>
  @GET("movie/{movieId}")
  fun getMovieById(@Path("movieId") movieId : String): Call<Film>
}