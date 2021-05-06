package com.example.mv_ted.view_model

import com.example.mv_ted.models.data.model.Movie
import com.example.mv_ted.models.data.model.RepositoryImpl

sealed class AppState{
    data class Success(val movieData: MutableList<Movie>) : AppState()
    data class Error(val error_ : Throwable) : AppState()
    object Loading: AppState()
}
