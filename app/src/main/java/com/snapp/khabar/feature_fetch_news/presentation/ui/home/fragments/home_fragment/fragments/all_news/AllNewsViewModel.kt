package com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.home_fragment.fragments.all_news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.google.firebase.firestore.Query
import com.snapp.khabar.feature_fetch_news.data.remote.FirebasePagingSource
import com.snapp.khabar.feature_fetch_news.domain.mappers.toArticleModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class AllNewsViewModel @Inject constructor(
    private val queryNewsBy: Query
): ViewModel() {

    /**
     * All News Flow
     * */
    val allNewsFlow = Pager(
        PagingConfig(
            2
        )
    ){
        FirebasePagingSource(queryNewsBy)
    }.flow.cachedIn(viewModelScope).map {
        it.map { articleDto ->
            articleDto.toArticleModel()
        }
    }
}