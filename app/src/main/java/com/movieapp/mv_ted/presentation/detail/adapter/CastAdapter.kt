package com.movieapp.mv_ted.presentation.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.movieapp.mv_ted.databinding.ItemActorItemBinding
import com.movieapp.mv_ted.domain.models.response.credits.ActorsResponse
import com.movieapp.mv_ted.domain.models.response.credits.Cast
import com.movieapp.mv_ted.utils.imageUri
import com.squareup.picasso.Picasso

class CastAdapter(
    private val credits: ActorsResponse
) : RecyclerView.Adapter<CastAdapter.CastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder =
        CastViewHolder(
            ItemActorItemBinding.inflate(LayoutInflater.from(parent.context))
        )

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bind(credits.cast[position])
    }

    override fun getItemCount(): Int = credits.cast.size

    inner class CastViewHolder(
        private val binding: ItemActorItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cast: Cast)= with(binding) {
             actorName.text = cast.originalName
             actorRole.text = cast.character
             Picasso
                 .get()
                 .load(imageUri + cast.profilePath)
                 .into(binding.moviePoster)
        }

    }
}