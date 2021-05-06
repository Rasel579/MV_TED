package com.example.mv_ted.models.data.model

interface Repository {

    fun getDataFromLocalStorage() : MutableList<Movie>
    fun getDataFromMovieAPI() : MutableList<Movie>

}