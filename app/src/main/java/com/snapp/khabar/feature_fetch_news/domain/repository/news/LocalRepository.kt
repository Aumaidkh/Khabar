package com.snapp.khabar.feature_fetch_news.domain.repository.news

import com.snapp.khabar.feature_fetch_news.data.local.entities.NewsEntity
import kotlinx.coroutines.flow.Flow

interface LocalRepository {

    suspend fun insertNewsEntity(newsEntity: NewsEntity)

    suspend fun deleteNewsEntity(newsEntity: NewsEntity)

    fun getNewsEntities(): Flow<List<NewsEntity>>
}