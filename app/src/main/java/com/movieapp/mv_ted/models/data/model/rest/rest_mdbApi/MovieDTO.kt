package com.movieapp.mv_ted.models.data.model.rest.rest_mdbApi

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDTO(val results: MutableList<MovieResultDTO>):Parcelable
