package com.snapp.khabar.feature_fetch_news.data.util

import com.google.firebase.firestore.QueryDocumentSnapshot
import com.snapp.khabar.feature_fetch_news.data.remote.dto.ArticleDto


fun QueryDocumentSnapshot.toArticleDto(): ArticleDto {
    val author = this.getString("author")
    val content = this.getString("content")
    val description = this.getString("description")
    val publishedAt = this.getLong("publishedAt")
    val source = this.getString("source")
    val title = this.getString("title")
    val url = this.getString("url")
    val urlToImage = this.getString("urlToImage")
    val id = this.getString("id")
    val category = this.getString("category")
    val isHeadline = this.getBoolean("isHeadline") ?: false

    return ArticleDto(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = null,
        title = title,
        url = url,
        urlToImage = urlToImage,
        isHeadline = isHeadline,
        category = category,
        id = id!!
    )
}

