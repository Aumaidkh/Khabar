package com.snapp.khabar.feature_fetch_news.data.repository.news

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.snapp.khabar.feature_fetch_news.data.remote.dto.ArticleDto
import com.snapp.khabar.feature_fetch_news.data.util.toArticleDto
import com.snapp.khabar.feature_fetch_news.domain.repository.news.RemoteNewsRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "FirebaseFirestoreReposi"
class FirebaseFirestoreRepositoryImpl @Inject constructor(
    private val firestoreReference: FirebaseFirestore
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
}