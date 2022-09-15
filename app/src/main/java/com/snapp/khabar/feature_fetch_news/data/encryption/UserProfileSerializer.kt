package com.snapp.khabar.feature_fetch_news.data.encryption

import android.util.Log
import androidx.datastore.core.Serializer
import com.snapp.khabar.feature_fetch_news.data.remote.dto.UserDto
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

private const val TAG = "UserProfileSerializer"
class UserProfileSerializer(
    private val cryptoManager: CryptoManager
): Serializer<UserDto> {

    override val defaultValue: UserDto
        get() = UserDto()

    /**
     * */
    override suspend fun readFrom(input: InputStream): UserDto {
        val decryptedBytes = cryptoManager.decrypt(input)
        return try {
            Json.decodeFromString<UserDto>(
                deserializer = UserDto.serializer(),
                string = decryptedBytes.decodeToString()
            )
        } catch (e: SerializationException){
            e.printStackTrace()
            Log.d(TAG, "Exception :${e.message.toString()}")
            defaultValue
        }
    }

    /**
     * Holds the encryption login*/
    override suspend fun writeTo(t: UserDto, output: OutputStream) {
        cryptoManager.encrypt(
            bytes = Json.encodeToString(
                serializer = UserDto.serializer(),
                value = t
            ).encodeToByteArray(),
            outputStream = output
        )
    }
}