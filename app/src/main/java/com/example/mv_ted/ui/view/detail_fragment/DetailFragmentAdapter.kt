package com.example.mv_ted.ui.view.detail_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mv_ted.databinding.ItemDetailBinding
import com.example.mv_ted.models.data.model.Comment
import com.example.mv_ted.ui.view.detail_fragment.DetailFragmentAdapter.*
import kotlinx.android.synthetic.main.item_detail.view.*

class DetailFragmentAdapter
    : RecyclerView.Adapter<DetailsViewHolder>() {
    private var listOfComments: List<Comment> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder =
        DetailsViewHolder(
        ItemDetailBinding
            .inflate(LayoutInflater.from(parent.context),parent, false ).root
        )
    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        holder.bind(listOfComments[position])
    }

    override fun getItemCount(): Int = listOfComments.size
    fun setData(commentsList: List<Comment>) {
         listOfComments = commentsList
         notifyDataSetChanged()
    }


    inner class DetailsViewHolder(itemView: View)  : RecyclerView.ViewHolder(itemView)  {
        fun bind(comment: Comment)=with(itemView) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                item_comment?.text = comment.comment
            }
        }

    }
}