package com.snapp.khabar.feature_fetch_news.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.snapp.khabar.feature_fetch_news.data.encryption.CryptoManager
import com.snapp.khabar.feature_fetch_news.data.encryption.UserProfileSerializer
import com.snapp.khabar.feature_fetch_news.data.local.UserPreferencesImpl
import com.snapp.khabar.feature_fetch_news.data.local.UserSettingsDatastoreManager
import com.snapp.khabar.feature_fetch_news.data.remote.dto.UserDto
import com.snapp.khabar.feature_fetch_news.domain.repository.user.UserPreferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun provideCryptoManager() =
        CryptoManager()



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


    @Singleton
    @Provides
    fun providePreferencesDataStore(
        @ApplicationContext appContext: Context,
        cryptoManager: CryptoManager
    ): DataStore<UserDto> {
        return DataStoreFactory.create(
            serializer = UserProfileSerializer(cryptoManager = cryptoManager),
            produceFile = {
                appContext.dataStoreFile("user-profile.json")
            },
            corruptionHandler = null,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        )
    }

    @Singleton
    @Provides
    fun provideUserPreferencesDatastore(
        dataStore: DataStore<UserDto>
    ): UserPreferencesDataStore {
        return UserPreferencesImpl(
            dataStore = dataStore
        )
    }

}