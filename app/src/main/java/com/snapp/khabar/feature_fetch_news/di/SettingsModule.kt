package com.snapp.khabar.feature_fetch_news.di

import com.snapp.khabar.feature_fetch_news.data.local.UserSettingsDatastoreManager
import com.snapp.khabar.feature_fetch_news.domain.use_cases.settings.DarkModeToggleUseCase
import com.snapp.khabar.feature_fetch_news.domain.use_cases.settings.CheckIfDarkModeIsEnabledUseCase
import com.snapp.khabar.feature_fetch_news.domain.use_cases.settings.CheckIfNotificationIsEnabledUseCase
import com.snapp.khabar.feature_fetch_news.domain.use_cases.settings.NotificationsToggleUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {


    @Provides
    @Singleton
    fun provideToggleDarkModeUseCase(
        userSettingsDatastoreManager: UserSettingsDatastoreManager
    ) =
        DarkModeToggleUseCase(userSettingsDatastoreManager)


    @Provides
    @Singleton
    fun provideIsNotificationEnabledUseCase(
        userSettingsDatastoreManager: UserSettingsDatastoreManager
    ) =
        CheckIfNotificationIsEnabledUseCase(userSettingsDatastoreManager)

    @Provides
    @Singleton
    fun provideIsDarkModeEnabledUseCase(
        userSettingsDatastoreManager: UserSettingsDatastoreManager
    ) =
        CheckIfDarkModeIsEnabledUseCase(userSettingsDatastoreManager)

    @Provides
    @Singleton
    fun provideToggleNotificationUseCase(
        userSettingsDatastoreManager: UserSettingsDatastoreManager
    ) =
        NotificationsToggleUseCase(userSettingsDatastoreManager)


}