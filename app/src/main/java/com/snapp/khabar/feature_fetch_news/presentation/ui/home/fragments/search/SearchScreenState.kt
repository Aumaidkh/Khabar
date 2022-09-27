package com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.search

import com.snapp.khabar.feature_fetch_news.domain.model.ArticleModel

data class SearchScreenState(
    val searchQuery: String? = "",
    val searchResults: List<ArticleModel> = emptyList()
)

data class SearchQuery(
    val searchQuery: String = ""
)

data class SearchResults(
    val results: List<ArticleModel> = emptyList()
)
