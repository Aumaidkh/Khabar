package com.snapp.khabar.feature_fetch_news.domain.use_cases.news.remote

import android.util.Log
import com.snapp.khabar.feature_fetch_news.data.remote.dto.ArticleDto
import com.snapp.khabar.feature_fetch_news.domain.repository.news.RemoteNewsRepository
import javax.inject.Inject

private const val TAG = "SearchNewsByUseCase"
class SearchNewsByUseCase @Inject constructor(
    private val repository: RemoteNewsRepository
) {

    suspend operator fun invoke(searchBy: String = "title", query: String): List<ArticleDto>{
        return repository.searchNewsBy(searchBy, query)
    }
}