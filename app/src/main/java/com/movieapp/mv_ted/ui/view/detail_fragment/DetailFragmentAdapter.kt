package com.movieapp.mv_ted.ui.view.detail_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.movieapp.mv_ted.databinding.ItemDetailBinding
import com.movieapp.mv_ted.models.data.model.Comment
import com.movieapp.mv_ted.ui.view.detail_fragment.DetailFragmentAdapter.DetailsViewHolder

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


    inner class DetailsViewHolder(private val binding: ItemDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: Comment) = with(itemView) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                binding.itemComment.text = comment.comment
            }
        }

    }
}