package com.snapp.khabar.feature_fetch_news.domain.repository.news

import androidx.paging.PagingData
import com.snapp.khabar.feature_fetch_news.data.remote.FirebaseArticleQuery
import com.snapp.khabar.feature_fetch_news.data.remote.dto.ArticleDto
import com.snapp.khabar.feature_fetch_news.data.util.Constants.SEARCH_BY_TITLE
import com.snapp.khabar.feature_fetch_news.domain.model.ArticleModel
import kotlinx.coroutines.flow.Flow

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

    /**
     * Fetches News By Category in terms of pages
     * */
    fun fetchNewsInPages(
        query: String,
        value: String,
        filter: Boolean

    ): Flow<PagingData<ArticleModel>>
}