package com.snapp.khabar.feature_fetch_news.domain.mappers

import com.snapp.khabar.feature_fetch_news.data.remote.dto.CommentDto
import com.snapp.khabar.feature_fetch_news.domain.model.CommentModel

fun CommentDto.toCommentModel(): CommentModel {
    return CommentModel(
        name = user_name,
        time = timestamp,
        comment = comment,
        imageUrl = user_img_url
    )
}