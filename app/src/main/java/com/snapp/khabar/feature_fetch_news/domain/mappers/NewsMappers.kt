package com.snapp.khabar.feature_fetch_news.domain.mappers

import android.util.Log
import com.snapp.khabar.feature_fetch_news.data.local.entities.NewsEntity
import com.snapp.khabar.feature_fetch_news.data.remote.dto.ArticleDto
import com.snapp.khabar.feature_fetch_news.domain.model.ArticleModel

private const val TAG = "NewsMappers"
fun ArticleDto.toArticleModel(): ArticleModel {
    Log.d(TAG, "ArticleDto toArticleModel: Mapping Id: $id")
    return ArticleModel(
        heading = title ?: "Title Unavailable",
        time = publishedAt,
        desc = description ?: "No description to show",
        image = urlToImage ?: "",
        url = url ?: "http:\\google.com",
        id = id
    )
}



fun NewsEntity.toArticleModel() : ArticleModel {
    Log.d(TAG, "NewsEntity toNewsModel: Id: $id")
    return ArticleModel(
        heading = title,
        desc = description,
        time = time,
        image = imageUrl,
        url = url,
        id = id
    )
}

fun ArticleModel.toNewsEntity() : NewsEntity {
    Log.d(TAG, "ArticleModel toNewsEntity: Id: $id")
    return NewsEntity(
        title = heading,
        description = desc,
        time = time!!,
        imageUrl = image,
        url = url,
        isBookmarked = true,
        id = id
    )
}