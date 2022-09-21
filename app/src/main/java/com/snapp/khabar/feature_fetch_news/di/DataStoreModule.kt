package com.snapp.khabar.feature_fetch_news.di

import android.content.Context
import com.snapp.khabar.feature_fetch_news.data.encryption.CryptoManager
import com.snapp.khabar.feature_fetch_news.data.local.DatastoreManager
import com.snapp.khabar.feature_fetch_news.data.local.UserSettingsDatastoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val USER_PROFILE_PREFS = "user-profile"

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun provideCryptoManager() =
        CryptoManager()

    @Singleton
    @Provides
    fun provideDatastoreManager(
        @ApplicationContext appContext: Context,
        cryptoManager: CryptoManager
    ): DatastoreManager {
        return DatastoreManager(
            context = appContext,
            cryptoManager = cryptoManager
        )
    }

    @Singleton
    @Provides
    fun provideUserSettingsDatastoreManager(
        @ApplicationContext appContext: Context,
        cryptoManager: CryptoManager
    ): UserSettingsDatastoreManager {
        return UserSettingsDatastoreManager(
            context = appContext,
            cryptoManager = cryptoManager
        )
    }

}