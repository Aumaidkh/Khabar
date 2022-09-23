package com.snapp.khabar.feature_fetch_news.data.repository.user

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.snapp.khabar.feature_fetch_news.data.remote.dto.UserDto
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<UserDto>
) {

    suspend fun saveUserInfo(userDto: UserDto){
        dataStore.updateData {
            UserDto(
                uid = userDto.uid,
                name = userDto.name,
                email = userDto.email,
                photoUrl = userDto.photoUrl,
                phoneNumber = userDto.phoneNumber
            )
        }
    }

    suspend fun getUserInfo() =
        dataStore.data.first()

}