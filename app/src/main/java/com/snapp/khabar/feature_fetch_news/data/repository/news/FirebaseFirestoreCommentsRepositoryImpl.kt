package com.snapp.khabar.feature_fetch_news.data.repository.news

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.snapp.khabar.feature_fetch_news.data.remote.dto.CommentDto
import com.snapp.khabar.feature_fetch_news.data.util.CollectionNames.COMMENTS
import com.snapp.khabar.feature_fetch_news.domain.repository.RemoteCommentsRepository
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

class FirebaseFirestoreCommentsRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): RemoteCommentsRepository {

    override suspend fun fetchCommentsForNews(newsId: String): List<CommentDto> {
        return firestore
            .collection(COMMENTS)
            .whereEqualTo("news_id",newsId)
            .get()
            .await()
            .map {
                Log.d(TAG, "fetchCommentsForNews: ${it.toObject(CommentDto::class.java)}")
                it.toObject(CommentDto::class.java)
            }

    }

    override suspend fun submitCommentForNews(
        commentDto: CommentDto
    ) {
        val id = UUID.randomUUID().toString()
        commentDto.id = id
        firestore
            .collection(COMMENTS)
            .document(id)
            .set(commentDto)
            .await()

    }
}

private const val TAG = "FirebaseFirestoreCommen"