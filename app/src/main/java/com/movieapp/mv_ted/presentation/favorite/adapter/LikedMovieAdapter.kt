package com.movieapp.mv_ted.presentation.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.movieapp.mv_ted.databinding.ItemLikesBinding
import com.movieapp.mv_ted.domain.models.Movie
import com.movieapp.mv_ted.models.data.model.imageUri
import com.squareup.picasso.Picasso

class LikedMovieAdapter : RecyclerView.Adapter<LikedMovieAdapter.LikedMovieViewHolder>() {
    private var listMovies: List<Movie> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikedMovieViewHolder =
        LikedMovieViewHolder(
            ItemLikesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: LikedMovieViewHolder, position: Int) {
        holder.bind(listMovies[position])
    }

    override fun getItemCount(): Int = listMovies.size
    fun setData(listLikedMovies: List<Movie>) {
        listMovies = listLikedMovies
        notifyDataSetChanged()
    }

    inner class LikedMovieViewHolder(private val binding: ItemLikesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) = with(binding) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                movieIdDateLikes.text = movie.date
                movieIdNameLikes.text = movie.title
                movieDescriptionLikes.text = movie.description
                Picasso.get()
                    .load(imageUri + movie.image)
                    .into(movieImageLike)
            }

        }

    }
}