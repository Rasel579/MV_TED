package com.movieapp.mv_ted.domain.models.response.credits

data class ActorsResponse(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)