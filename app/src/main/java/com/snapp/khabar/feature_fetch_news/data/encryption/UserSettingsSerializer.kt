package com.snapp.khabar.feature_fetch_news.data.encryption

import androidx.datastore.core.Serializer
import com.snapp.khabar.feature_fetch_news.domain.model.UserSettings
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

class UserSettingsSerializer(
    private val cryptoManager: CryptoManager
): Serializer<UserSettings> {

    override val defaultValue: UserSettings
        get() = UserSettings()

    override suspend fun readFrom(input: InputStream): UserSettings {
        val decryptedBytes = cryptoManager.decrypt(input)
        return try {
            Json.decodeFromString(
                deserializer = UserSettings.serializer(),
                string = decryptedBytes.decodeToString()
            )
        } catch (e: SerializationException){
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: UserSettings, output: OutputStream) {
        cryptoManager.encrypt(
            bytes = Json.encodeToString(
                serializer = UserSettings.serializer(),
                value = t
            ).encodeToByteArray(),
            outputStream = output
        )
    }
}