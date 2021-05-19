package com.example.mv_ted.models.data.model.rest.retrofit_rest

import com.example.mv_ted.models.data.model.rest.rest_mdbApi.MovieDTO
import com.example.mv_ted.models.data.model.rest.rest_mdbApi.MovieResultDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface BackendApi {
  @GET("popular")
  fun getMoviesList(): Call<MovieDTO>
  @GET("upcoming")
  fun getMoviesListUpcoming(): Call<MovieDTO>
}