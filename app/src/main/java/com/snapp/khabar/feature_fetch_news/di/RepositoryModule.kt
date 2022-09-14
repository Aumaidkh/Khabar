package com.snapp.khabar.feature_fetch_news.di

import com.google.firebase.firestore.FirebaseFirestore
import com.snapp.khabar.feature_fetch_news.data.repository.FirebaseFirestoreCommentsRepositoryImpl
import com.snapp.khabar.feature_fetch_news.data.repository.FirebaseFirestoreRepositoryImpl
import com.snapp.khabar.feature_fetch_news.domain.repository.RemoteCommentsRepository
import com.snapp.khabar.feature_fetch_news.domain.repository.RemoteNewsRepository
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
}