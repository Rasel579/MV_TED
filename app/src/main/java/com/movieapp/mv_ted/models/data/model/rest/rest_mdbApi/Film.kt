package com.movieapp.mv_ted.models.data.model.rest.rest_mdbApi

import com.google.gson.annotations.SerializedName

data class Film(
    @SerializedName("production_countries")
    val productionCompanies : MutableList<CompanyProductionDTO>
)
