package com.snapp.khabar.feature_fetch_news.presentation.ui.comment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.snapp.khabar.R
import com.snapp.khabar.feature_fetch_news.domain.model.CommentModel
import com.snapp.khabar.feature_fetch_news.presentation.util.HelperFunctions.getTimestampFromMillis

class CommentsAdapter: RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>() {

    private var commentsList = emptyList<CommentModel>()


    class CommentViewHolder(private val itemView: View): RecyclerView.ViewHolder(itemView){
        /*Binding Name*/
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        /*Binding Body*/
        private val tvComment: TextView = itemView.findViewById(R.id.tvComment)
        /*Binding Image*/
        private val ivCommentImage: ImageView = itemView.findViewById(R.id.ivCommentImage)
        /*Binding Timestamp*/
        private val tvTimestamp: TextView = itemView.findViewById(R.id.tvTimeStamp)
        /*See More Button*/
        private val tvSeeMore: TextView = itemView.findViewById(R.id.seeMore)

        fun bind(commentModel: CommentModel){
            tvName.text = commentModel.name
            tvComment.text = commentModel.comment
            tvTimestamp.text = getTimestampFromMillis(commentModel.time)

            Glide.with(itemView.context)
                .load(commentModel.imageUrl)
                .placeholder(R.drawable.user)
                .error(R.drawable.user)
                .into(ivCommentImage)

            tvSeeMore.setOnClickListener {
                tvComment.maxLines = 20
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment_layout,parent,false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = commentsList[position]
        holder.bind(comment)
    }

    override fun getItemCount() = commentsList.size


    fun submitData(comments: List<CommentModel>){
        commentsList = comments
        notifyDataSetChanged()
    }

}