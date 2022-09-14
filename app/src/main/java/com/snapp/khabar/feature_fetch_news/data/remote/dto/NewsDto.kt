package com.snapp.khabar.feature_fetch_news.data.remote.dto


import com.google.gson.annotations.SerializedName

data class NewsDto(
    @SerializedName("articles")
    val articleDtos: List<ArticleDto>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)