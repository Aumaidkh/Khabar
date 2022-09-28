package com.snapp.khabar.feature_fetch_news.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.snapp.khabar.feature_fetch_news.data.remote.dto.ArticleDto
import kotlinx.coroutines.tasks.await

class FirebasePagingSource(
    private val queryNewsByCategory: Query
) : PagingSource<QuerySnapshot, ArticleDto>(){

    override fun getRefreshKey(state: PagingState<QuerySnapshot, ArticleDto>): QuerySnapshot? {
        return null
    }

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, ArticleDto> {
        return try {
            val currentPage = params.key ?: queryNewsByCategory.get().await()
            val lastVisibleNewsItem = currentPage.documents[currentPage.size() - 1]
            val nextPage = queryNewsByCategory.startAfter(lastVisibleNewsItem).get().await()
            LoadResult.Page(
                data = currentPage.toObjects(ArticleDto::class.java),
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}