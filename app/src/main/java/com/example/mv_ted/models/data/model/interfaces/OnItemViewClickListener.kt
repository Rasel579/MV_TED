package com.example.mv_ted.models.data.model.interfaces

import com.example.mv_ted.models.data.model.Movie

interface OnItemViewClickListener {
   fun onItemClickListener(movie: Movie)
}