package com.snapp.khabar.feature_fetch_news.data.repository.news

import com.snapp.khabar.feature_fetch_news.data.local.entities.NewsDataBase
import com.snapp.khabar.feature_fetch_news.data.local.entities.NewsEntity
import com.snapp.khabar.feature_fetch_news.domain.repository.news.LocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val db: NewsDataBase
): LocalRepository {

    override suspend fun insertNewsEntity(newsEntity: NewsEntity){
        db.getNewsDao().insertNewsEntity(newsEntity)

    }

    override suspend fun deleteNewsEntity(newsEntity: NewsEntity){
        db.getNewsDao().deleteNewsEntity(newsEntity)
    }

    override fun getNewsEntities(): Flow<List<NewsEntity>>{
        return db.getNewsDao().getAllNewsEntities()

    }

}