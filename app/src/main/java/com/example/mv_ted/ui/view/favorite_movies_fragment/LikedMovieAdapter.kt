package com.example.mv_ted.ui.view.favorite_movies_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mv_ted.databinding.ItemLikesBinding
import com.example.mv_ted.models.data.model.Movie
import com.example.mv_ted.models.data.model.imageUri
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_likes.view.*

class LikedMovieAdapter : RecyclerView.Adapter<LikedMovieAdapter.LikedMovieViewHolder>(){
    private var listMovies : List<Movie> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikedMovieViewHolder =
        LikedMovieViewHolder(ItemLikesBinding.inflate(LayoutInflater.from(parent.context), parent, false).root)
    override fun onBindViewHolder(holder: LikedMovieViewHolder, position: Int) {
        holder.bind(listMovies[position])
    }

    override fun getItemCount(): Int = listMovies.size
    fun setData(listLikedMovies : List<Movie>){
        listMovies = listLikedMovies
        notifyDataSetChanged()
    }

    inner class LikedMovieViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bind(movie: Movie)= with(itemView) {
            if(layoutPosition != RecyclerView.NO_POSITION){
                 movie_id_date_likes.text = movie.date
                 movie_id_name_likes.text = movie.title
                 movie_description_likes.text = movie.description
                Picasso.get()
                    .load(imageUri + movie.image)
                    .into(movie_image_like)
            }

        }

    }
}