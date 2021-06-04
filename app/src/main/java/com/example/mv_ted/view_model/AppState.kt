package com.example.mv_ted.view_model

import com.example.mv_ted.models.data.model.Comment
import com.example.mv_ted.models.data.model.Movie
import com.example.mv_ted.models.data.model.rest.rest_mdbApi.MovieResultDTO

sealed class AppState{
    data class Success(val movieDataNow: MutableList<MovieResultDTO>?, val movieDataUpcoming: MutableList<MovieResultDTO>?) : AppState()
    data class Error(val error_ : Throwable) : AppState()
    object Loading: AppState()
    data class SuccessDetailsFrg(val commentsList: List<Comment>) : AppState()
    data class SuccessLike(val likedMoviesList: List<Movie>) : AppState()
    data class SuccessFilmCountry(val country: String) : AppState()
}
