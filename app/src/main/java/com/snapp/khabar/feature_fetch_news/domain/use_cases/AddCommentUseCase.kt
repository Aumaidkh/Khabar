package com.snapp.khabar.feature_fetch_news.domain.use_cases

import com.snapp.khabar.feature_fetch_news.domain.repository.RemoteCommentsRepository
import com.snapp.khabar.feature_fetch_news.domain.repository.RemoteNewsRepository
import javax.inject.Inject

class AddCommentUseCase @Inject constructor(
    private val repository: RemoteCommentsRepository
) {
}