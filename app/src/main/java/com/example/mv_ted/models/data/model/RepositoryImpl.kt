package com.example.mv_ted.models.data.model

import com.example.mv_ted.models.data.model.rest_mdbApi.MovieResultDTO
import com.example.mv_ted.models.data.model.rest_mdbApi.MoviesLoader
import java.net.URL

class RepositoryImpl() : Repository {
     override fun getDataFromServer(uri: URL): MutableList<MovieResultDTO>?  = MoviesLoader.loadMovies(uri)
     override fun getDataFromLocalStorage() = getDataMovie()
     override fun getDataFromMovieAPI() = getDataMovie()

 }