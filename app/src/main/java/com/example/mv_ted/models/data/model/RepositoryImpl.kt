package com.example.mv_ted.models.data.model

import java.util.*

open class RepositoryImpl() : Repository {
    private lateinit var listMovies: List<Movie>;
    override fun init(): List<Movie> {
        listMovies = listOf<Movie>(
            Movie("1 film", Calendar.DAY_OF_MONTH, 0),
            Movie("2 film", Calendar.DAY_OF_MONTH, 0)
        )
        return listMovies
    }

    override fun getMovie(position: Int): Movie {
        return listMovies[position]
    }

    override fun getSize(): Int {
        return listMovies.size
    }

    override fun addMovie(movie: Movie) {
    }

    override fun removeMovie(movie: Movie) {
        TODO("Not yet implemented")
    }

    override fun updateMovie(movie: Movie, position: Int) {
        TODO("Not yet implemented")
    }

}
