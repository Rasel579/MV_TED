package com.example.mv_ted.models.data.model.rest.rest_mdbApi

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class MovieResultDTO(
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("poster_path")
    val posterPath: String) : Parcelable
