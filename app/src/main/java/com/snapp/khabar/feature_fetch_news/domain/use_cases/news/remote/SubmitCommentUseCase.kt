package com.snapp.khabar.feature_fetch_news.domain.use_cases.news.remote

import com.snapp.khabar.feature_fetch_news.data.remote.dto.CommentDto
import com.snapp.khabar.feature_fetch_news.domain.repository.news.RemoteCommentsRepository
import com.snapp.khabar.feature_fetch_news.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SubmitCommentUseCase @Inject constructor(
    private val remoteCommentsRepository: RemoteCommentsRepository
) {

    operator fun invoke(
        userId: String,
        userName: String,
        comment: String,
        newsId: String,
        imageUrl: String
    ): Flow<Result<String>> {
        val commentDto = CommentDto(
            user_id = userId,
            user_name = userName,
            comment = comment,
            timestamp = System.currentTimeMillis(),
            news_id = newsId,
            user_img_url = imageUrl
        )
        return flow {
            emit(Result.Loading())
            try {
                remoteCommentsRepository.submitCommentForNews(commentDto)
                emit(Result.Success("Comment Submitted"))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }
    }
}