package com.snapp.khabar.feature_fetch_news.domain.repository

import com.snapp.khabar.feature_fetch_news.data.remote.dto.ArticleDto

interface RemoteNewsRepository {

    suspend fun fetchNewsFrom(category: String): List<ArticleDto>

}