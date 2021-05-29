package com.example.mv_ted.models.data.model.rest.retrofit_rest

import com.example.mv_ted.models.data.model.rest.rest_mdbApi.Film
import com.example.mv_ted.models.data.model.rest.rest_mdbApi.MovieDTO
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