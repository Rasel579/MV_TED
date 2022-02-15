package com.movieapp.mv_ted.domain.models.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDTO(val results: MutableList<MovieResultDTO>):Parcelable
