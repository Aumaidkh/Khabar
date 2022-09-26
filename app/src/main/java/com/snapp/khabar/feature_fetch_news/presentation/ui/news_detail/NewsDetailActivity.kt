package com.snapp.khabar.feature_fetch_news.presentation.ui.news_detail


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.snapp.khabar.R
import com.snapp.khabar.databinding.ActivityNewsDetailBinding
import com.snapp.khabar.feature_fetch_news.core.DataBindingActivity
import com.snapp.khabar.feature_fetch_news.domain.model.ArticleModel
import com.snapp.khabar.feature_fetch_news.presentation.util.getFormattedTime


class NewsDetailActivity : DataBindingActivity<ActivityNewsDetailBinding>(R.layout.activity_news_detail) {

    private val newsArgs: NewsDetailActivityArgs by navArgs()

    override fun ActivityNewsDetailBinding.initialize() {
        val newsItem = newsArgs.newsItem

        // Setting onClick Listeners
        setupOnClickListeners()


        // Setting data on widgets
        setDataOnUi(newsItem)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = resources.getColor(R.color.transparent)
        super.onCreate(savedInstanceState)


    }


    private fun setupOnClickListeners() {
       binding.apply {
           // Adding back button functionality
           backKey.setOnClickListener {
               finish()
           }

           tvUrl.setOnClickListener {
               Intent(Intent.ACTION_VIEW).apply {
                   data = tvUrl.text.toString().toUri()
               }.also {
                   startActivity(it)
               }
           }
       }
    }


    private fun setDataOnUi(item: ArticleModel?){
        binding.apply {
            if (item == null){
                return
            }
            tvNewsTitle.text = item.heading
            // newsDescTv.text = item.description
            tvTimeStamp.text = item.time!!.getFormattedTime()

            // Load data in webview
            tvNewsDesc.text = item.desc

            tvUrl.text = getString(R.string.url_with_place_holder,item.url)

            // Setting image through glide
            Glide.with(this@NewsDetailActivity)
                .load(item.image)
                .into(ivNewsImage)
        }
    }


}