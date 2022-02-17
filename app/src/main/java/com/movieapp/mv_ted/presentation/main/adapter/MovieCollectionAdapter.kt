package com.movieapp.mv_ted.presentation.main.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.movieapp.mv_ted.databinding.ItemCardMovieBinding
import com.movieapp.mv_ted.models.data.model.imageUri
import com.movieapp.mv_ted.presentation.main.listeners.OnItemViewClickListener
import com.movieapp.mv_ted.domain.models.response.MovieResponse
import com.squareup.picasso.Picasso

class MovieCollectionAdapter(
    private var listMovies: MutableList<MovieResponse>?,
    private val onItemViewClickListener: OnItemViewClickListener
) :
    RecyclerView.Adapter<MovieCollectionAdapter.ViewHolder>() {
    private lateinit var _binding : ItemCardMovieBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        _binding = ItemCardMovieBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(_binding.root)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listMovies?.get(position)?.let { holder.setData(it) }
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int = listMovies?.size ?: 0

   inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var titleTextMovie: TextView = let { _binding.titleView }
        private var imageViewMovie: AppCompatImageView = let { _binding.ImageView}
        private var dateUpcomingMovie: TextView = let { _binding.dateUpcoming }

        @RequiresApi(Build.VERSION_CODES.M)
        fun setData(movie: MovieResponse) {
            titleTextMovie.text = movie.originalTitle
            dateUpcomingMovie.text = movie.releaseDate
            Picasso.get()
                .load(imageUri + movie.posterPath)
                .into(imageViewMovie)
            _binding.root.setOnClickListener {
                onItemViewClickListener.onItemClickListener(movie)
            }
        }
    }

}


