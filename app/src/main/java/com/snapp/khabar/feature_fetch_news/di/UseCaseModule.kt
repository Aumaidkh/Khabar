package com.snapp.khabar.feature_fetch_news.di

import com.snapp.khabar.feature_fetch_news.domain.use_cases.auth.AuthenticateUserWithGoogleUseCase
import com.snapp.khabar.feature_fetch_news.domain.use_cases.auth.SignOutUseCase
import com.snapp.khabar.feature_fetch_news.domain.use_cases.news.remote.SubmitCommentUseCase
import com.snapp.khabar.feature_fetch_news.domain.repository.auth.AuthRepository
import com.snapp.khabar.feature_fetch_news.domain.repository.auth.EmailAndPasswordAuth
import com.snapp.khabar.feature_fetch_news.domain.repository.news.PhotoRepository
import com.snapp.khabar.feature_fetch_news.domain.repository.news.RemoteCommentsRepository
import com.snapp.khabar.feature_fetch_news.domain.repository.news.RemoteNewsRepository
import com.snapp.khabar.feature_fetch_news.domain.repository.user.UserPreferencesDataStore
import com.snapp.khabar.feature_fetch_news.domain.repository.user.UserRepository
import com.snapp.khabar.feature_fetch_news.domain.use_cases.*
import com.snapp.khabar.feature_fetch_news.domain.use_cases.auth.CheckIfUserIsAuthenticatedUseCase
import com.snapp.khabar.feature_fetch_news.domain.use_cases.auth.CreateUserWithEmailAndPasswordUseCase
import com.snapp.khabar.feature_fetch_news.domain.use_cases.auth.SignInWithEmailAndPasswordUseCase
import com.snapp.khabar.feature_fetch_news.domain.use_cases.news.remote.FetchAllCommentsForNews
import com.snapp.khabar.feature_fetch_news.domain.use_cases.news.remote.FetchNewsFromFirebaseFirestoreUseCase
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
    fun provideSaveUserToDatabaseUseCase(repository: UserRepository): SaveUserIntoFirestoreUseCase {
        return SaveUserIntoFirestoreUseCase(repository)
    }


}