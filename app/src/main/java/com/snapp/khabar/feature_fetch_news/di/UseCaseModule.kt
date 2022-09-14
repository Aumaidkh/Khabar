package com.snapp.khabar.feature_fetch_news.di

import com.snapp.khabar.feature_fetch_news.data.repository.SubmitCommentUseCase
import com.snapp.khabar.feature_fetch_news.domain.repository.RemoteCommentsRepository
import com.snapp.khabar.feature_fetch_news.domain.repository.RemoteNewsRepository
import com.snapp.khabar.feature_fetch_news.domain.use_cases.FetchAllCommentsForNews
import com.snapp.khabar.feature_fetch_news.domain.use_cases.FetchNewsFromFirebaseFirestoreUseCase
import com.snapp.khabar.feature_fetch_news.domain.use_cases.ValidateCommentUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {


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

    @Provides
    @Singleton
    fun provideValidateCommentUseCase(): ValidateCommentUseCase {
        return ValidateCommentUseCase()
    }

}