package com.example.mv_ted.models.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
data class Movie(val title: String?, val date: String?, val image: String?, val description: String?) : Parcelable
