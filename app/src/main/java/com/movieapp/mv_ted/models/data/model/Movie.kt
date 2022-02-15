package com.movieapp.mv_ted.models.data.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(val title: String?, val date: String?, val image: String?, val description: String?): Parcelable
