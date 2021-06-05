package com.movieapp.mv_ted.models.data.model.interfaces

import com.movieapp.mv_ted.models.data.model.rest.rest_mdbApi.MovieResultDTO

interface OnItemViewClickListener {
   fun onItemClickListener(movie: MovieResultDTO)
}