package com.snapp.khabar.feature_fetch_news.data.remote.dto


import com.google.gson.annotations.SerializedName

data class ArticleDto(
    @SerializedName("author")
    val author: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("publishedAt")
    val publishedAt: Long?,
    @SerializedName("source")
    val source: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("urlToImage")
    val urlToImage: String?,
    @SerializedName("isHeadline")
    val isHeadline: Boolean,
    val category: String? = null,
    val id: String
){
    constructor() : this("","","",0,null,"","","",false,null,"")
}