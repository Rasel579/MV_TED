package com.movieapp.mv_ted.data.datasource.cloudsource.api

import com.movieapp.mv_ted.domain.models.response.movie.Film
import com.movieapp.mv_ted.domain.models.response.movie.MovieDTO
import com.movieapp.mv_ted.domain.models.response.movie.MovieResponse
import com.movieapp.mv_ted.domain.models.response.credits.ActorsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BackendApi {
  @GET("movie/popular")
  suspend fun getMoviesList(): MovieDTO
  @GET("movie/popular/")
  suspend fun getMoviesListPagination(@Query("page") page: Int): MovieDTO
  @GET("movie/upcoming")
  fun getMoviesListUpcoming(): Call<MovieDTO>
  @GET("movie/{movieId}")
  suspend fun getMovieById(@Path("movieId") movieId : String): MovieResponse

  @GET("movie/{movieId}")
  fun getMovieByIdCallBack(@Path("movieId") movieId : String): Call<Film>

  @GET("movie/{movieId}/credits")
  suspend fun getCredits(@Path("movieId") movieId : String): ActorsResponse
}