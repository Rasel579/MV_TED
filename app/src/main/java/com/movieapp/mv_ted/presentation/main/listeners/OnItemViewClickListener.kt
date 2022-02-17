package com.movieapp.mv_ted.presentation.main.listeners

import com.movieapp.mv_ted.domain.models.response.MovieResponse

interface OnItemViewClickListener {
   fun onItemClickListener(movie: MovieResponse)
}