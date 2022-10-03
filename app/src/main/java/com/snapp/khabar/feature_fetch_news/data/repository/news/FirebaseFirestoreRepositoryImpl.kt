package com.snapp.khabar.feature_fetch_news.data.repository.news

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.snapp.khabar.feature_fetch_news.data.remote.FirebaseArticleQuery
import com.snapp.khabar.feature_fetch_news.data.remote.FirebasePagingSource
import com.snapp.khabar.feature_fetch_news.data.remote.dto.ArticleDto
import com.snapp.khabar.feature_fetch_news.data.util.toArticleDto
import com.snapp.khabar.feature_fetch_news.domain.mappers.toArticleModel
import com.snapp.khabar.feature_fetch_news.domain.model.ArticleModel
import com.snapp.khabar.feature_fetch_news.domain.repository.news.RemoteNewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "FirebaseFirestoreReposi"
class FirebaseFirestoreRepositoryImpl @Inject constructor(
    private val firestoreReference: FirebaseFirestore,
    private val firebaseQuery: FirebaseArticleQuery
): RemoteNewsRepository {

    override suspend fun fetchNewsFrom(category: String): List<ArticleDto>{
        return firestoreReference
            .collection(category)
            .get()
            .await()
            .map {
                it.toArticleDto()
            }

    }

    override suspend fun searchNewsBy(searchBy: String, query: String): List<ArticleDto>{
        return firestoreReference
            .collection("AllNews")
            .whereGreaterThan(searchBy,query)
            .whereLessThanOrEqualTo(searchBy,"$query\uF7FF")
            .get()
            .await()
            .map {
                it.toArticleDto()
            }
    }

    override fun fetchNewsInPages(
        query: String,
        value: String,
        filter: Boolean
    ): Flow<PagingData<ArticleModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1
            )
        ) {
            FirebasePagingSource(
                query = query,
                value = value,
                applyFilter = filter,
                firebaseArticleQuery = firebaseQuery
            )
        }.flow
    }
}