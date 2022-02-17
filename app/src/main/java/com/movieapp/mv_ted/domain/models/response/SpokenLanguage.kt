package com.movieapp.mv_ted.domain.models.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpokenLanguage(
    val english_name: String,
    val iso_639_1: String,
    val name: String
): Parcelable