package com.movieapp.mv_ted.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.movieapp.mv_ted.databinding.ItemCardMovieBinding
import com.movieapp.mv_ted.domain.models.response.movie.MovieResponse
import com.movieapp.mv_ted.utils.imageUri
import com.movieapp.mv_ted.presentation.main.listeners.OnItemViewClickListener
import com.squareup.picasso.Picasso

class MovieCollectionAdapter(
    private var listMovies: MutableList<MovieResponse>?,
    private val onItemViewClickListener: OnItemViewClickListener,
    private val getData: (Int) -> Unit
) : RecyclerView.Adapter<MovieCollectionAdapter.ViewHolder>() {
    private var page = 1
    private val positionToPreLoad = 5

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(
        ItemCardMovieBinding.inflate(LayoutInflater.from(parent.context))
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listMovies?.get(position)?.let { holder.setData(it) }
        holder.setIsRecyclable(false)
        if (position == listMovies?.size?.minus(positionToPreLoad)) {
            page++
            getData.invoke(page)
        }
    }

    override fun getItemCount(): Int = listMovies?.size ?: 0

    fun addPageData(data: MutableList<MovieResponse>?) {
        data?.let { listMovies?.addAll(it) }
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        private val viewBinding: ItemCardMovieBinding
    ) : RecyclerView.ViewHolder(viewBinding.root) {
        private val convertToDecimal = 10
        fun setData(movie: MovieResponse) = with(viewBinding) {
            titleView.text = movie.originalTitle
            dateUpcoming.text = movie.releaseDate
            ratioText.text = movie.voteAverage.toString()
            Picasso.get()
                .load(imageUri + movie.posterPath)
                .into(moviePoster)
            progressRatio.progress = (movie.voteAverage * convertToDecimal).toInt()
            viewBinding.root.setOnClickListener {
                onItemViewClickListener.onItemClickListener(movie)
            }
        }
    }

}


