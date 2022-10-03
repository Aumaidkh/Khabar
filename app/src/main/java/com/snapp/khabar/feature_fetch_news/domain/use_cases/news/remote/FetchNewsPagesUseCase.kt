package com.snapp.khabar.feature_fetch_news.domain.use_cases.news.remote

import androidx.paging.PagingData
import com.snapp.khabar.feature_fetch_news.domain.model.ArticleModel
import com.snapp.khabar.feature_fetch_news.domain.repository.news.RemoteNewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Fetches news in pages with
 * where query is equal to value
 * E.g, title = Hello
 * */
class FetchNewsPagesUseCase @Inject constructor(
    private val repository: RemoteNewsRepository
) {

    operator fun invoke(query: String, value: String = "",applyFilter: Boolean): Flow<PagingData<ArticleModel>>{
        return repository.fetchNewsInPages(
            query = query,
            value = value,
            filter = applyFilter
        )
    }
}