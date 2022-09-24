package com.snapp.khabar.feature_fetch_news.di

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.snapp.khabar.feature_fetch_news.data.repository.auth.AuthRepositoryImpl
import com.snapp.khabar.feature_fetch_news.data.repository.auth.EmailAndPasswordAuthImpl
import com.snapp.khabar.feature_fetch_news.data.repository.news.FirebaseFirestoreCommentsRepositoryImpl
import com.snapp.khabar.feature_fetch_news.data.repository.news.FirebaseFirestoreRepositoryImpl
import com.snapp.khabar.feature_fetch_news.data.repository.news.PhotoRepositoryImpl
import com.snapp.khabar.feature_fetch_news.data.repository.user.UserRepositoryImpl
import com.snapp.khabar.feature_fetch_news.domain.repository.auth.AuthRepository
import com.snapp.khabar.feature_fetch_news.domain.repository.auth.EmailAndPasswordAuth
import com.snapp.khabar.feature_fetch_news.domain.repository.news.PhotoRepository
import com.snapp.khabar.feature_fetch_news.domain.repository.news.RemoteCommentsRepository
import com.snapp.khabar.feature_fetch_news.domain.repository.news.RemoteNewsRepository
import com.snapp.khabar.feature_fetch_news.domain.repository.user.UserPreferencesDataStore
import com.snapp.khabar.feature_fetch_news.domain.repository.user.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    /**
     * Email And Password Auth Repository
     * */
    @Provides
    @Singleton
    fun provideEmailAndPasswordAuthRepository(
        firebaseAuth: FirebaseAuth
    ): EmailAndPasswordAuth {
        return EmailAndPasswordAuthImpl(firebaseAuth)
    }

    // Provide Remote Repository
    @Provides
    @Singleton
    fun providesRemoteRepository(firestore: FirebaseFirestore): RemoteNewsRepository {
        return FirebaseFirestoreRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun providesRemoteCommentsRepository(firestore: FirebaseFirestore): RemoteCommentsRepository {
        return FirebaseFirestoreCommentsRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun providesUserRepository(
        firebaseAuth: FirebaseFirestore,
        userPreferencesDataStore: UserPreferencesDataStore
    ): UserRepository {
        return UserRepositoryImpl(firebaseAuth, userPreferencesDataStore)
    }

    @Provides
    @Singleton
    fun providesAuthRepository(
        firebaseAuth: FirebaseAuth,
        googleSignInClient: GoogleSignInClient
    ): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth, googleSignInClient)
    }

    /**
     * Provide Photo repository here
     * */
    @Provides
    @Singleton
    fun providePhotoRepository(
        storageReference: StorageReference
    ): PhotoRepository {
        return PhotoRepositoryImpl(storageReference)
    }
}