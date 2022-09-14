package com.snapp.khabar.feature_fetch_news.domain.use_cases

import android.util.Log
import com.snapp.khabar.feature_fetch_news.data.remote.dto.CommentDto
import com.snapp.khabar.feature_fetch_news.domain.mappers.toCommentModel
import com.snapp.khabar.feature_fetch_news.domain.model.CommentModel
import com.snapp.khabar.feature_fetch_news.domain.repository.RemoteCommentsRepository
import com.snapp.khabar.feature_fetch_news.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val TAG = "FetchAllCommentsForNews"
class FetchAllCommentsForNews @Inject constructor(
    private val commentsRepository: RemoteCommentsRepository
) {

    operator fun invoke(newsId: String): Flow<Result<List<CommentModel>>> {
        return flow {
            emit(Result.Loading())
            val comment = commentsRepository.fetchCommentsForNews(newsId).map { it.toCommentModel() }
            Log.d(TAG, "invoke: $comment")
            emit(Result.Success(
                commentsRepository.fetchCommentsForNews(newsId).map { it.toCommentModel() }
            ))
        }
    }
}