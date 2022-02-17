package com.movieapp.mv_ted.domain.models.response.movie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductionCountry(
    val iso_3166_1: String,
    val name: String
): Parcelable