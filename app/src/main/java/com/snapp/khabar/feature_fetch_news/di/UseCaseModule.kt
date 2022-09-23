package com.snapp.khabar.feature_fetch_news.di

import com.snapp.khabar.feature_fetch_news.data.repository.AuthenticateUserWithGoogleUseCase
import com.snapp.khabar.feature_fetch_news.data.repository.SignOutUseCase
import com.snapp.khabar.feature_fetch_news.data.repository.SubmitCommentUseCase
import com.snapp.khabar.feature_fetch_news.domain.repository.*
import com.snapp.khabar.feature_fetch_news.domain.use_cases.*
import com.snapp.khabar.feature_fetch_news.domain.use_cases.auth.CheckIfUserIsAuthenticatedUseCase
import com.snapp.khabar.feature_fetch_news.domain.use_cases.user.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    /**
     * Save User Details*/
    @Provides
    @Singleton
    fun provideSaveUserToDataStoreUseCase(
        userPreferencesDataStore: UserPreferencesDataStore
    ): SaveUserDataToDataStoreUseCase {
        return SaveUserDataToDataStoreUseCase(userPreferencesDataStore)
    }

    /**
     * Fetch User Details
     * */
    @Provides
    @Singleton
    fun provideFetchUserFromDataStoreUseCase(
        userPreferencesDataStore: UserPreferencesDataStore
    ): FetchUserFromDataStoreUseCase {
        return FetchUserFromDataStoreUseCase(userPreferencesDataStore)
    }

    /**
     * Upload Photo User Case
     * */
    @Provides
    @Singleton
    fun provideUploadPhotoUseCase(
        repository: PhotoRepository
    ): UploadProfilePhotoUseCase {
        return UploadProfilePhotoUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateUserUseCase(
        repository: UserRepository
    ): UpdateUserUseCase {
        return UpdateUserUseCase(repository)
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