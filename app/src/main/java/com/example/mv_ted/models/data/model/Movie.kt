package com.example.mv_ted.models.data.model

import android.os.Parcelable
import com.example.mv_ted.R
import kotlinx.android.parcel.Parcelize
import java.util.*
@Parcelize
data class Movie(val title: String, val date: String, val image: String) : Parcelable
