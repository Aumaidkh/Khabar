package com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.home_fragment.fragments.all_news.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.snapp.khabar.R
import com.snapp.khabar.feature_fetch_news.domain.model.ArticleModel
import com.snapp.khabar.feature_fetch_news.presentation.ui.comment.CommentsActivity
import com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.bookmark.BookmarkFragmentDirections
import com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.home_fragment.HomeFragmentDirections
import com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.search.SearchFragmentDirections
import com.snapp.khabar.feature_fetch_news.presentation.util.getFormattedTime
import com.snapp.khabar.feature_fetch_news.presentation.util.showShareIntent

class NewsAdapter(
    val adapterParent: Int,
    val onBookmarkClick: (articleModel: ArticleModel) -> Unit
) : RecyclerView.Adapter<NewsAdapter.ArticleModelViewHolder>(){
    var newsList = emptyList<ArticleModel>()

    inner class ArticleModelViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val heading: TextView = itemView.findViewById(R.id.tvNewsTitle)
        private val desc: TextView = itemView.findViewById(R.id.tvNewsDesc)
        private val time: TextView  = itemView.findViewById(R.id.tvTimeStamp)
        private val image: ImageView  = itemView.findViewById(R.id.ivNewsImage)
        private val bookmarkBtn: ImageButton = itemView.findViewById(R.id.btnBookMark)
        private val shareBtn: ImageButton = itemView.findViewById(R.id.btnShare)
        private val commentBtn: ImageButton = itemView.findViewById(R.id.btnComments)


        fun bind(news : ArticleModel){
            heading.text = news.heading
            desc.text = news.desc
            time.text = news.time!!.getFormattedTime()
            Glide.with(itemView.context)
                .load(news.image)
                .into(image)

            itemView.setOnClickListener {
                navigateToDetailsScreen(news)

            }
            /**
             * BookMark Button Listener here
             * */
            bookmarkBtn.setOnClickListener {
                onBookmarkClick.invoke(news)
                Snackbar.make(itemView,"Item Saved", Snackbar.LENGTH_SHORT).show()
            }

            /**
             * Comment Button Listener here
             * */
            commentBtn.setOnClickListener{
                Intent(itemView.context, CommentsActivity::class.java).apply {
                    Log.d("TAG", "News Id: ${news.id}")
                    putExtra("newsId",news.id)
                }.also {
                    itemView.context.startActivity(it)
                }

            }

            /**
             * Share Button Listener
             * */
            shareBtn.setOnClickListener {
                itemView.context.showShareIntent(
                    news.url
                )
            }
        }


        private fun navigateToDetailsScreen(news: ArticleModel) {

            val extras = FragmentNavigatorExtras(
                image to "imageTransition"
            )

            val action = when (adapterParent) {
                1 -> {
                    HomeFragmentDirections.actionHomeToNewsDetailActivity(news)

                }
                2 -> {
                    SearchFragmentDirections.actionSearchToNewsDetailActivity(news)

                }
                else -> {
                    BookmarkFragmentDirections.actionBookmarksToNewsDetailActivity(news)

                }
            }
            itemView.findNavController().navigate(action,extras)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleModelViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.news_item_layout, parent, false)
        return ArticleModelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleModelViewHolder, position: Int) {
        val currentArticleModel = newsList[position]
        holder.bind(currentArticleModel)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    fun submitData(newList: List<ArticleModel>){
       newsList = newList
       notifyDataSetChanged()

    }
}