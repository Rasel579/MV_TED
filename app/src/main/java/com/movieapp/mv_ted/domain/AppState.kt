package com.movieapp.mv_ted.domain

import com.movieapp.mv_ted.domain.models.Comment
import com.movieapp.mv_ted.domain.models.Movie
import com.movieapp.mv_ted.domain.models.response.credits.ActorsResponse
import com.movieapp.mv_ted.domain.models.response.movie.MovieResponse

sealed class AppState{
    data class Success(val movieDataNow: MutableList<MovieResponse>?) : AppState()
    data class Error(val error_ : Throwable) : AppState()
    object Loading: AppState()
    data class SuccessPagination(val movieDataNow: MutableList<MovieResponse>?) : AppState()
    data class SuccessDetailsFrg(val commentsList: List<Comment>) : AppState()
    data class SuccessLike(val likedMoviesList: List<Movie>) : AppState()
    data class SuccessFilmCountry(val country: String) : AppState()
    data class  SuccessMovieId(val movie: MovieResponse) : AppState()
    data class SuccessCredits(val credits: ActorsResponse): AppState()
}
