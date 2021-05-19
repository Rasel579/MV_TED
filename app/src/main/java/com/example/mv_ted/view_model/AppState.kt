package com.example.mv_ted.view_model

import com.example.mv_ted.models.data.model.rest_mdbApi.MovieDTO
import com.example.mv_ted.models.data.model.rest_mdbApi.MovieResultDTO

sealed class AppState{
    data class Success(val movieDataNow: MutableList<MovieResultDTO>?, val movieDataUpcoming: MutableList<MovieResultDTO>?) : AppState()
    data class Error(val error_ : Throwable) : AppState()
    object Loading: AppState()
}
