package com.snapp.khabar.feature_fetch_news.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CommentDto(
    @SerializedName("id")
    var id: String = "",
    @SerializedName("user_id")
    var user_id: String = "",
    @SerializedName("news_id")
    val news_id: String = "",
    @SerializedName("user_name")
    val user_name: String = "",
    @SerializedName("user_img_url")
    val user_img_url: String = "",
    @SerializedName("comment")
    val comment: String = "",
    @SerializedName("timestamp")
    val timestamp: Long = 0
)