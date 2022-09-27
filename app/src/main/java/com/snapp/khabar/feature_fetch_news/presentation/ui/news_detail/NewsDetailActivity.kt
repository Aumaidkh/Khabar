package com.snapp.khabar.feature_fetch_news.presentation.ui.news_detail


import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.snapp.khabar.R
import com.snapp.khabar.databinding.ActivityNewsDetailBinding
import com.snapp.khabar.feature_fetch_news.core.DataBindingActivity
import com.snapp.khabar.feature_fetch_news.domain.model.ArticleModel
import com.snapp.khabar.feature_fetch_news.presentation.util.getFormattedTime
import com.snapp.khabar.feature_fetch_news.presentation.util.openIntentInBrowser
import com.snapp.khabar.feature_fetch_news.presentation.util.showShareIntent
import com.snapp.khabar.feature_fetch_news.presentation.util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class NewsDetailActivity :
    DataBindingActivity<ActivityNewsDetailBinding>(R.layout.activity_news_detail) {

    private val newsArgs: NewsDetailActivityArgs by navArgs()
    private val viewModel: NewsDetailsViewModel by viewModels()

    override fun ActivityNewsDetailBinding.initialize() {
        val newsItem = newsArgs.newsItem

        // Setting onClick Listeners
        setupOnClickListeners(newsItem)

        consumeFlows()

        // Setting data on widgets
        setDataOnUi(newsItem)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = resources.getColor(R.color.transparent)
        super.onCreate(savedInstanceState)


    }


    private fun setupOnClickListeners(newsItem: ArticleModel) {
        binding.apply {
            // Adding back button functionality
            backKey.setOnClickListener {
                viewModel.onEvent(NewsDetailsEvents.UserEvents.BackButtonClick)
            }

            /**
             * Opens Up the Link in browser
             * */
            tvUrl.setOnClickListener {
                openIntentInBrowser(newsItem.url)
            }

            /**
             * Opens Up the Share Intent With the url of the news in it
             * */
            btnShare.setOnClickListener {
                viewModel.onEvent(NewsDetailsEvents.UserEvents.ShareButtonClick(newsItem.url))
            }

            /**
             * Bookmarks the news item
             * */
            btnBookMark.setOnClickListener {
                viewModel.onEvent(NewsDetailsEvents.UserEvents.BookmarkButtonClick(newsItem))
            }
        }
    }

    private fun setDataOnUi(item: ArticleModel?) {
        binding.apply {
            if (item == null) {
                return
            }
            tvNewsTitle.text = item.heading
            // newsDescTv.text = item.description
            tvTimeStamp.text = item.time!!.getFormattedTime()

            // Load data in webview
            tvNewsDesc.text = item.desc

            tvUrl.text = getString(R.string.url_with_place_holder, item.url)

            // Setting image through glide
            Glide.with(this@NewsDetailActivity)
                .load(item.image)
                .into(ivNewsImage)
        }
    }

    private fun consumeFlows() {
        /**
         * Consume Ui Event Flow*/
        lifecycleScope.launchWhenStarted {
            viewModel.eventFlow.collectLatest { event ->
                when(event){
                    is NewsDetailsEvents.UiEvents.ShowSnackBar -> {
                        binding.root.showSnackBar(event.message)
                    }

                    is NewsDetailsEvents.UiEvents.CloseScreen -> {
                        finish()
                    }

                    is NewsDetailsEvents.UiEvents.NavigateToShareScreen -> {
                        showShareIntent(event.link)
                    }
                }
            }
        }
    }


}