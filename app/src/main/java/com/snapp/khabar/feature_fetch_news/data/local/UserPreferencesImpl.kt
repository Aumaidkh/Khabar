package com.snapp.khabar.feature_fetch_news.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.snapp.khabar.feature_fetch_news.data.encryption.CryptoManager
import com.snapp.khabar.feature_fetch_news.data.encryption.UserProfileSerializer
import com.snapp.khabar.feature_fetch_news.data.remote.dto.UserDto
import com.snapp.khabar.feature_fetch_news.domain.repository.UserPreferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.serialization.serializer
import javax.inject.Inject

class UserPreferencesImpl @Inject constructor(
    val dataStore: DataStore<UserDto>
) : UserPreferencesDataStore {



    override suspend fun getProfileDetails(): UserDto {
        return dataStore.data.first()
    }

    override suspend fun saveUserInfo(userDto: UserDto) {
        dataStore.updateData {
            UserDto(
                uid = userDto.uid,
                name = userDto.name,
                email = userDto.email,
                photoUrl = userDto.photoUrl,
                phoneNumber = userDto.phoneNumber,
                gender = userDto.gender
            )
        }
    }
}