package com.snapp.khabar.feature_fetch_news.di

import com.snapp.khabar.feature_fetch_news.domain.repository.news.NewsRepository
import com.snapp.khabar.feature_fetch_news.domain.repository.news.RemoteCommentsRepository
import com.snapp.khabar.feature_fetch_news.domain.repository.news.RemoteNewsRepository
import com.snapp.khabar.feature_fetch_news.domain.use_cases.news.remote.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsUseCasesModule {

    @Provides
    @Singleton
    fun providesRemoteNewsUseCases(
        commentsForNews: FetchAllCommentsForNews,
        allNewsUseCase: FetchAllNewsUseCase,
        headlinesUseCase: FetchHeadlinesUseCase,
        newsFromFirebaseFirestoreUseCase: FetchNewsFromFirebaseFirestoreUseCase,
        submitCommentUseCase: SubmitCommentUseCase,
        searchNewsByUseCase: SearchNewsByUseCase
    ): RemoteNewsUseCases {
        return RemoteNewsUseCases(
            commentsForNews = commentsForNews,
            allNewsUseCase = allNewsUseCase,
            headlinesUseCase = headlinesUseCase,
            newsFromFirebaseFirestoreUseCase = newsFromFirebaseFirestoreUseCase,
            submitCommentUseCase = submitCommentUseCase,
            searchNewsByUseCase = searchNewsByUseCase
        )
    }

    /**
     * Search News Use Case
     * */
    @Provides
    @Singleton
    fun provideSearchNewsFromFirestoreUseCase(repository: RemoteNewsRepository): SearchNewsByUseCase {
        return SearchNewsByUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideFetchNewsFromFirestoreUseCase(repository: RemoteNewsRepository): FetchNewsFromFirebaseFirestoreUseCase {
        return FetchNewsFromFirebaseFirestoreUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideFetchCommentsFromFirestoreUseCase(repository: RemoteCommentsRepository): FetchAllCommentsForNews {
        return FetchAllCommentsForNews(repository)
    }

    @Provides
    @Singleton
    fun provideSubmitCommentUseCase(repository: RemoteCommentsRepository): SubmitCommentUseCase {
        return SubmitCommentUseCase(repository)
    }

    // Injecting Use cases
    // Provide Fetch News Use Case
    @Provides
    @Singleton
    fun provideFetchUseCase(repository: NewsRepository): FetchAllNewsUseCase {
        return FetchAllNewsUseCase(repository)
    }

    // Provide Fetch Headlines Use Case
    @Provides
    @Singleton
    fun provideHeadlinesUseCase(repository: RemoteNewsRepository): FetchHeadlinesUseCase {
        return FetchHeadlinesUseCase(repository)
    }
}