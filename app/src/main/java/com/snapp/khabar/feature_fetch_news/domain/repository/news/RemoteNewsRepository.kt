package com.snapp.khabar.feature_fetch_news.domain.repository.news

import com.snapp.khabar.feature_fetch_news.data.remote.dto.ArticleDto
import com.snapp.khabar.feature_fetch_news.data.util.Constants.SEARCH_BY_TITLE

interface RemoteNewsRepository {

    /**
     * Fetches News of a specific category
     * @param category
     * @return list of all Articles
     * */
    suspend fun fetchNewsFrom(category: String): List<ArticleDto>

    /**
     * Fetches List of News Item
     * @param searchBy,
     * @param query
     * where searchBy is equal to query
     * @return list of articles
     * */
    suspend fun searchNewsBy(searchBy: String = SEARCH_BY_TITLE, query: String): List<ArticleDto>
}