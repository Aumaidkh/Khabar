package com.snapp.khabar.feature_fetch_news.data.local

import android.content.Context
import androidx.datastore.dataStore
import com.snapp.khabar.feature_fetch_news.data.encryption.CryptoManager
import com.snapp.khabar.feature_fetch_news.data.encryption.UserProfileSerializer
import com.snapp.khabar.feature_fetch_news.data.remote.dto.UserDto
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DatastoreManager @Inject constructor(
    private val context: Context,
    private val cryptoManager: CryptoManager
) {

    private val Context.datastore by dataStore(
        fileName = "user-profile-details.json",
        serializer = UserProfileSerializer(cryptoManager)
    )

    suspend fun getProfileDetails() =
        context.datastore.data.first()


    suspend fun saveUserInfo(userDto: UserDto){
        context.datastore.updateData {
            UserDto(
                uid = userDto.uid,
                name = userDto.name,
                email = userDto.email,
                photoUrl = userDto.photoUrl,
                phoneNumber = userDto.phoneNumber
            )
        }
    }
}