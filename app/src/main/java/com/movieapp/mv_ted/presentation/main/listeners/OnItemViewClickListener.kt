package com.movieapp.mv_ted.presentation.main.listeners

import com.movieapp.mv_ted.domain.models.response.MovieResultDTO

interface OnItemViewClickListener {
   fun onItemClickListener(movie: MovieResultDTO)
}