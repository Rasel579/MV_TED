package com.movieapp.mv_ted.presentation.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.movieapp.mv_ted.databinding.ItemDetailBinding
import com.movieapp.mv_ted.domain.models.Comment
import com.movieapp.mv_ted.presentation.detail.adapter.DetailFragmentAdapter.DetailsViewHolder

class DetailFragmentAdapter
    : RecyclerView.Adapter<DetailsViewHolder>() {
    private var listOfComments: List<Comment> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder =
        DetailsViewHolder(
            ItemDetailBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        holder.bind(listOfComments[position])
    }

    override fun getItemCount(): Int = listOfComments.size
    fun setData(commentsList: List<Comment>) {
        listOfComments = commentsList
        notifyDataSetChanged()
    }


    inner class DetailsViewHolder(
        private val binding: ItemDetailBinding
        ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: Comment) = with(binding) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemComment.text = comment.comment
            }
        }

    }
}