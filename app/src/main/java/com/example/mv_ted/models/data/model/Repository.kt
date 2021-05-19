package com.example.mv_ted.models.data.model

import com.example.mv_ted.models.data.model.rest.rest_mdbApi.MovieDTO
import com.example.mv_ted.models.data.model.rest.rest_mdbApi.MovieResultDTO
import retrofit2.Callback
import java.net.URL

interface Repository {
    fun getDataFromServer(uri: URL) : MutableList<MovieResultDTO>?
    fun getDataFromServerRetrofit(callback : Callback<MovieDTO>)
    fun getDataFromServerRetrofitUpcoming(callback : Callback<MovieDTO>)
    fun getDataFromLocalStorage() : MutableList<Movie>
    fun getDataFromMovieAPI() : MutableList<Movie>
}