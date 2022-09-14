package com.snapp.khabar.feature_fetch_news.domain.model

data class CommentModel (
    val name : String,
    val comment : String,
    val time : Long,
    val imageUrl: String
)


fun getDummyComments() =
    emptyList<CommentModel>()