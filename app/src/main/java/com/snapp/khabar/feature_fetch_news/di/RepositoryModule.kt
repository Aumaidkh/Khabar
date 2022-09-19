package com.snapp.khabar.feature_fetch_news.di

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.snapp.khabar.feature_fetch_news.data.local.DatastoreManager
import com.snapp.khabar.feature_fetch_news.data.repository.*
import com.snapp.khabar.feature_fetch_news.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

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
        datastoreManager: DatastoreManager
    ): UserRepository {
        return UserRepositoryImpl(firebaseAuth, datastoreManager)
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