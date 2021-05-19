package com.example.mv_ted.models.data.model.rest_mdbApi

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class MovieResultDTO(
    val original_title: String,
    val overview: String,
    val release_date: String,
    val poster_path: String) : Parcelable
