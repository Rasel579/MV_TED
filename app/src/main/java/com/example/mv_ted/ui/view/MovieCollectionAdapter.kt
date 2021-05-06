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
import com.example.mv_ted.models.data.model.Movie
import com.example.mv_ted.models.data.model.interfaces.OnItemViewClickListener

class MovieCollectionAdapter(
    private var listMovies: MutableList<Movie>,
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
        holder.setData(listMovies[position])
    }

    override fun getItemCount() = listMovies.size


   inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTextMovie: TextView? = null
        var imageViewMovie: AppCompatImageView? = null
        var dateUpcomingMovie: TextView? = null

        init {
            titleTextMovie = _binding.titleView
            imageViewMovie = _binding.ImageView
            dateUpcomingMovie = _binding.dateUpcoming
        }



        @RequiresApi(Build.VERSION_CODES.M)
        fun setData(movie: Movie) {
            titleTextMovie?.text = movie.title
            dateUpcomingMovie?.text = movie.date
            imageViewMovie?.setImageResource(movie.image)
            _binding.root.setOnClickListener {
                onItemViewClickListener.onItemClickListener(movie)
            }
        }
    }

}


