package com.example.mv_ted.models.data.model

interface Repository {

    fun getMovie(position: Int) : Movie
    fun getSize() : Int
    fun addMovie(movie: Movie)
    fun removeMovie(movie: Movie)
    fun updateMovie(movie: Movie, position: Int)
    companion object fun init(): List<Movie>
}