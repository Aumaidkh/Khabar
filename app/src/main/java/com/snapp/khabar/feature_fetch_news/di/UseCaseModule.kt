package com.snapp.khabar.feature_fetch_news.di

import com.snapp.khabar.feature_fetch_news.data.local.DatastoreManager
import com.snapp.khabar.feature_fetch_news.data.repository.AuthenticateUserWithGoogleUseCase
import com.snapp.khabar.feature_fetch_news.data.repository.SignOutUseCase
import com.snapp.khabar.feature_fetch_news.data.repository.SubmitCommentUseCase
import com.snapp.khabar.feature_fetch_news.domain.repository.AuthRepository
import com.snapp.khabar.feature_fetch_news.domain.repository.RemoteCommentsRepository
import com.snapp.khabar.feature_fetch_news.domain.repository.RemoteNewsRepository
import com.snapp.khabar.feature_fetch_news.domain.repository.UserRepository
import com.snapp.khabar.feature_fetch_news.domain.use_cases.*
import com.snapp.khabar.feature_fetch_news.domain.use_cases.auth.CheckIfUserIsAuthenticatedUseCase
import com.snapp.khabar.feature_fetch_news.domain.use_cases.user.SaveUserIntoFirestoreUseCase
import com.snapp.khabar.feature_fetch_news.domain.use_cases.user.UpdateUserUseCase
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
    fun provideUpdateUserUseCase(
        repository: UserRepository,
        datastoreManager: DatastoreManager
    ): UpdateUserUseCase {
        return UpdateUserUseCase(repository, datastoreManager)
    }

    @Provides
    @Singleton
    fun provideSignOutUseCase(repository: AuthRepository): SignOutUseCase {
        return SignOutUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCheckIfUserIsAuthenticatedUseCase(repository: AuthRepository): CheckIfUserIsAuthenticatedUseCase {
        return CheckIfUserIsAuthenticatedUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveUserToDatabaseUseCase(repository: UserRepository): SaveUserIntoFirestoreUseCase {
        return SaveUserIntoFirestoreUseCase(repository)
    }


    @Provides
    @Singleton
    fun provideAuthenticateUserUseCase(repository: AuthRepository): AuthenticateUserWithGoogleUseCase {
        return AuthenticateUserWithGoogleUseCase(repository)
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

}