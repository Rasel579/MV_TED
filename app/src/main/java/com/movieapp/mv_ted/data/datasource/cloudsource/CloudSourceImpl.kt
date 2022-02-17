package com.movieapp.mv_ted.data.datasource.cloudsource

import com.movieapp.mv_ted.data.datasource.cloudsource.api.BackendApi
import com.movieapp.mv_ted.data.datasource.cloudsource.api.BackendRepo
import com.movieapp.mv_ted.domain.models.response.credits.ActorsResponse
import com.movieapp.mv_ted.domain.models.response.movie.Film
import com.movieapp.mv_ted.domain.models.response.movie.MovieDTO
import com.movieapp.mv_ted.domain.models.response.movie.MovieResponse
import retrofit2.Call

class CloudSourceImpl(
    private val api: BackendApi = BackendRepo.api
) : CloudSource {
    override suspend fun getMoviesList(): MovieDTO = api.getMoviesList()

    override suspend fun getMoviesListPagination(page: Int): MovieDTO =
        api.getMoviesListPagination(page)

    override fun getMoviesListUpcoming(): Call<MovieDTO> = api.getMoviesListUpcoming()

    override suspend fun getMovieById(movieId: String): MovieResponse = api.getMovieById(movieId)

    override fun getMovieByIdCallBack(movieId: String): Call<Film> =
        api.getMovieByIdCallBack(movieId)

    override suspend fun getCredits(movieId: String): ActorsResponse = api.getCredits(movieId)
}