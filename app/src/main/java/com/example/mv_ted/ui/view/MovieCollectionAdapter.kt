package com.example.mv_ted.ui.view

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.mv_ted.R
import com.example.mv_ted.models.data.model.Movie
import com.example.mv_ted.models.data.model.RepositoryImpl

class MovieCollectionAdapter(private var listMovies: RepositoryImpl) :
    RecyclerView.Adapter<MovieCollectionAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieCollectionAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return ViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: MovieCollectionAdapter.ViewHolder, position: Int) {
        listMovies = RepositoryImpl()
        listMovies.init()
        holder.setData(listMovies.getMovie(position))
    }

    override fun getItemCount() = listMovies.getSize()


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTextMovie: TextView? = null

        var imageViewMovie: AppCompatImageView? = null
        var dateUpcomingMovie: TextView? = null

        init {
            titleTextMovie = itemView.findViewById(R.id.title_view)
            imageViewMovie = itemView.findViewById(R.id.ImageView)
            dateUpcomingMovie = itemView.findViewById(R.id.dateUpcoming)
        }

        @RequiresApi(Build.VERSION_CODES.M)
        fun setData(movie: Movie) {
            titleTextMovie?.text = movie.title
            dateUpcomingMovie?.text = movie.date.toString()
        }
    }

}


