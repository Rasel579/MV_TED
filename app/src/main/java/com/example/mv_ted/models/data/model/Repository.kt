package com.example.mv_ted.models.data.model

import com.example.mv_ted.models.data.model.rest_mdbApi.MovieDTO
import com.example.mv_ted.models.data.model.rest_mdbApi.MovieResultDTO
import java.net.URL

interface Repository {
    fun getDataFromServer(uri: URL) : MutableList<MovieResultDTO>?
    fun getDataFromLocalStorage() : MutableList<Movie>
    fun getDataFromMovieAPI() : MutableList<Movie>

}