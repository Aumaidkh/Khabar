package com.snapp.khabar.feature_fetch_news.data.repository.news

import com.snapp.khabar.feature_fetch_news.data.remote.NewsApi
import com.snapp.khabar.feature_fetch_news.data.remote.dto.NewsDto
import com.snapp.khabar.feature_fetch_news.domain.repository.news.NewsRepository
import retrofit2.Response
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApi
): NewsRepository {

    /**
     * Fetching latest news from the api */
    override suspend fun fetchLatestNews(options: Map<String,String>): Response<NewsDto> {
        return api.fetchTopHeadlines(options)
    }

    /**
     * Fetching news related to everything*/
    override suspend fun fetchEverythingNews(options: Map<String, String>): Response<NewsDto> {
        return api.fetchEverything(options)
    }
}