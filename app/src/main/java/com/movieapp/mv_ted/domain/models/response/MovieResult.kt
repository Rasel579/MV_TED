package com.movieapp.mv_ted.domain.models.response

import android.os.Parcelable
import androidx.versionedparcelable.ParcelField
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieResultDTO(
    val id : String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    val adult: Boolean,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("poster_path")
    val posterPath: String): Parcelable
