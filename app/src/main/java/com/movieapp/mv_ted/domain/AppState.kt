package com.movieapp.mv_ted.domain

import com.movieapp.mv_ted.domain.models.Comment
import com.movieapp.mv_ted.domain.models.Movie
import com.movieapp.mv_ted.domain.models.response.MovieResultDTO

sealed class AppState{
    data class Success(val movieDataNow: MutableList<MovieResultDTO>?, val movieDataUpcoming: MutableList<MovieResultDTO>?) : AppState()
    data class Error(val error_ : Throwable) : AppState()
    object Loading: AppState()
    data class SuccessDetailsFrg(val commentsList: List<Comment>) : AppState()
    data class SuccessLike(val likedMoviesList: List<Movie>) : AppState()
    data class SuccessFilmCountry(val country: String) : AppState()
}
