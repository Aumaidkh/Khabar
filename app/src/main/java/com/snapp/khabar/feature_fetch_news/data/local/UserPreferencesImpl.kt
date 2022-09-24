package com.snapp.khabar.feature_fetch_news.data.local

import androidx.datastore.core.DataStore
import com.snapp.khabar.feature_fetch_news.data.remote.dto.UserDto
import com.snapp.khabar.feature_fetch_news.domain.repository.user.UserPreferencesDataStore
import kotlinx.coroutines.flow.first
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