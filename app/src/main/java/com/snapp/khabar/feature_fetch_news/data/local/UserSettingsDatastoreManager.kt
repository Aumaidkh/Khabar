package com.snapp.khabar.feature_fetch_news.data.local

import android.content.Context
import androidx.datastore.dataStore
import com.snapp.khabar.feature_fetch_news.data.encryption.CryptoManager
import com.snapp.khabar.feature_fetch_news.data.encryption.UserProfileSerializer
import com.snapp.khabar.feature_fetch_news.data.encryption.UserSettingsSerializer
import com.snapp.khabar.feature_fetch_news.data.remote.dto.UserDto
import com.snapp.khabar.feature_fetch_news.domain.model.UserSettings
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserSettingsDatastoreManager @Inject constructor(
    private val context: Context,
    cryptoManager: CryptoManager
) {

    private val Context.datastore by dataStore(
        fileName = "user-settings.json",
        serializer = UserSettingsSerializer(cryptoManager)
    )

    fun getUserSettings() =
        context.datastore.data

    suspend fun isDarkModeEnabled() =
        context.datastore.data.first().isDarkModeEnabled


    suspend fun isNotificationEnabled() =
        context.datastore.data.first().isNotificationEnabled



    suspend fun enableDarkMode(enable: Boolean){
        context.datastore.updateData {
            UserSettings(
                isDarkModeEnabled = enable,
                isNotificationEnabled = context.datastore.data.first().isNotificationEnabled
            )
        }
    }


    suspend fun enableNotifications(enable: Boolean){
        context.datastore.updateData {
            UserSettings(
                isDarkModeEnabled = context.datastore.data.first().isDarkModeEnabled,
                isNotificationEnabled = enable
            )
        }
    }
}