package com.snapp.khabar.feature_fetch_news.domain.use_cases.news.remote

import javax.inject.Inject

data class RemoteNewsUseCases @Inject constructor(
    val commentsForNews: FetchAllCommentsForNews,
    val allNewsUseCase: FetchAllNewsUseCase,
    val headlinesUseCase: FetchHeadlinesUseCase,
    val newsFromFirebaseFirestoreUseCase: FetchNewsFromFirebaseFirestoreUseCase,
    val submitCommentUseCase: SubmitCommentUseCase,
    val searchNewsByUseCase: SearchNewsByUseCase
)
