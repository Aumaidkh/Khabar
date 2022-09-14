package com.snapp.khabar.feature_fetch_news.presentation.ui.comment.adapters

import com.snapp.khabar.feature_fetch_news.presentation.ui.comment.CommentsEvent

sealed class CommentsScreenEvents{

    data class SubmitCommentEvent(
        val userId: String = "223232323",
        val comment: String,
        val userName: String = "Murtaza",
        val newsId: String,
        val userImageUrl: String = ""
    ): CommentsScreenEvents()

    data class OnCommentChange(
        val comment: String
    ): CommentsScreenEvents()

    data class FetchComments(val newsId: String): CommentsScreenEvents()
}
