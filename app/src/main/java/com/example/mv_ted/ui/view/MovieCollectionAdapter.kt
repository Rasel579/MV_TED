package com.example.mv_ted.ui.view

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.mv_ted.databinding.ItemBinding
import com.example.mv_ted.models.data.model.interfaces.OnItemViewClickListener
import com.example.mv_ted.models.data.model.rest_mdbApi.MovieResultDTO

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
    }

    override fun getItemCount(): Int = listMovies?.size!!


   inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var titleTextMovie: TextView = let { _binding.titleView }
        var imageViewMovie: AppCompatImageView = let { _binding.ImageView}
        private var dateUpcomingMovie: TextView = let { _binding.dateUpcoming }

        @RequiresApi(Build.VERSION_CODES.M)
        fun setData(movie: MovieResultDTO) {
            titleTextMovie.text = movie.original_title
            dateUpcomingMovie.text = movie.release_date
            _binding.root.setOnClickListener {
                onItemViewClickListener.onItemClickListener(movie)
            }
        }
    }

}


