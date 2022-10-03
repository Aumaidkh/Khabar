package com.snapp.khabar.feature_fetch_news.data.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.snapp.khabar.feature_fetch_news.data.remote.dto.ArticleDto
import com.snapp.khabar.feature_fetch_news.domain.mappers.toArticleModel
import com.snapp.khabar.feature_fetch_news.domain.model.ArticleModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "FirebasePagingSource"
class FirebasePagingSource (
    private val firebaseArticleQuery: FirebaseArticleQuery,
    private val query: String,
    private val value: String,
    private val applyFilter: Boolean
) : PagingSource<QuerySnapshot, ArticleModel>() {

    override fun getRefreshKey(state: PagingState<QuerySnapshot, ArticleModel>): QuerySnapshot? {
        return null
    }

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, ArticleModel> {
        return try {
            val currentPage =
                params.key ?: firebaseArticleQuery
                    .getQueryCategory(
                        query = query,
                        value = value,
                        applyCategoryFilter = applyFilter
                    )
                    .get()
                    .await()
            val lastVisibleNewsItem = currentPage.documents[currentPage.size() - 1]
            val nextPage = params.key ?: firebaseArticleQuery
                .getQueryCategory(
                    query = query,
                    value = value,
                    applyCategoryFilter = applyFilter
                )
                .startAfter(lastVisibleNewsItem).get().await()
            LoadResult.Page(
                data = currentPage.toObjects(ArticleDto::class.java).map { it.toArticleModel() },
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }


    }
}