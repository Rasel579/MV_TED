package com.example.mv_ted.models.data.model

import com.example.mv_ted.models.data.model.rest.rest_mdbApi.MovieDTO
import com.example.mv_ted.models.data.model.rest.rest_mdbApi.MovieResultDTO
import com.example.mv_ted.models.data.model.rest.rest_mdbApi.MoviesLoader
import com.example.mv_ted.models.data.model.rest.retrofit_rest.BackendRepo
import retrofit2.Callback
import java.net.URL

class RepositoryImpl : Repository {
    override fun getDataFromServer(uri: URL): MutableList<MovieResultDTO>?  = MoviesLoader.loadMovies(uri)
    override fun getDataFromServerRetrofit(callback: Callback<MovieDTO>) {
        BackendRepo.api.getMoviesList().enqueue(callback)
    }

    override fun getDataFromServerRetrofitUpcoming(callback: Callback<MovieDTO>) {
       BackendRepo.api.getMoviesListUpcoming().enqueue(callback)
    }

    override fun getDataFromLocalStorage() = getDataMovie()
    override fun getDataFromMovieAPI() = getDataMovie()

 }