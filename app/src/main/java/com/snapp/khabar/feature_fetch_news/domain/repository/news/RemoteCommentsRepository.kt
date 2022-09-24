package com.snapp.khabar.feature_fetch_news.domain.repository.news

import com.snapp.khabar.feature_fetch_news.data.remote.dto.CommentDto
import com.snapp.khabar.feature_fetch_news.data.remote.dto.SubmitCommentResponseDto

interface RemoteCommentsRepository {

    suspend fun fetchCommentsForNews(newsId: String): List<CommentDto>

    suspend fun submitCommentForNews(commentDto: CommentDto)
}