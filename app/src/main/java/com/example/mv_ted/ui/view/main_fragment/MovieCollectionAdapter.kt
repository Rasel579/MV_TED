package com.example.mv_ted.ui.view.main_fragment

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.mv_ted.databinding.ItemBinding
import com.example.mv_ted.models.data.model.imageUri
import com.example.mv_ted.models.data.model.interfaces.OnItemViewClickListener
import com.example.mv_ted.models.data.model.rest.rest_mdbApi.MovieResultDTO
import com.squareup.picasso.Picasso

class MovieCollectionAdapter(
    private var listMovies: MutableList<MovieResultDTO>?,
    private val onItemViewClickListener: OnItemViewClickListener
) :
    RecyclerView.Adapter<MovieCollectionAdapter.ViewHolder>() {
    private lateinit var _binding : ItemBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        _binding = ItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
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
        fun setData(movie: MovieResultDTO) {
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


