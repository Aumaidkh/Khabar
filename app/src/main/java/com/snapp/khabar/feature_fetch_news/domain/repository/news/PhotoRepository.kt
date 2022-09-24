package com.snapp.khabar.feature_fetch_news.domain.repository.news

import android.net.Uri

interface PhotoRepository {

    /**
     * Uploads the profile photo for the user with
     * @param userId
     * @param imageUri
     * @return photoUrl
     * */
    suspend fun uploadUserProfilePhoto(userId: String, imageUri: Uri): String?
}