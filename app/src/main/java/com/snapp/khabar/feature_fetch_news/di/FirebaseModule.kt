package com.snapp.khabar.feature_fetch_news.di

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    // Provide Firebase Firestore Instance
    @Provides
    @Singleton
    fun providesFirebaseFirestoreInstance(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun providesProvidesFirebaseAuth() =
        FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesFirebaseStorageInstance() =
        FirebaseStorage.getInstance("gs://khabar-df21c.appspot.com")


    @Provides
    @Singleton
    fun providesFirebaseStorageReference(
        firebaseStorage: FirebaseStorage
    ): StorageReference {
        return firebaseStorage.reference
    }


    @Provides
    @Singleton
    fun provideQueryNewsByName() = FirebaseFirestore.getInstance()
        .collection("AllNews")
        .orderBy("publishedAt",Query.Direction.DESCENDING)
        .limit(10.toLong())



}