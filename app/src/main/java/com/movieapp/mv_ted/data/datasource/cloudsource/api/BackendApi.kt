package com.movieapp.mv_ted.data.datasource.cloudsource.api

import com.movieapp.mv_ted.domain.models.response.Film
import com.movieapp.mv_ted.domain.models.response.MovieDTO
import com.movieapp.mv_ted.domain.models.response.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BackendApi {
  @GET("movie/popular")
  fun getMoviesList(): Call<MovieDTO>
  @GET("movie/upcoming")
  fun getMoviesListUpcoming(): Call<MovieDTO>
  @GET("movie/{movieId}")
  suspend fun getMovieById(@Path("movieId") movieId : String): MovieResponse

  @GET("movie/{movieId}")
  fun getMovieByIdCallBack(@Path("movieId") movieId : String): Call<Film>
}