package com.movieapp.mv_ted.domain.models.response

import com.google.gson.annotations.SerializedName

data class Film(
    @SerializedName("production_countries")
    val productionCompanies : MutableList<CompanyProductionDTO>
)
