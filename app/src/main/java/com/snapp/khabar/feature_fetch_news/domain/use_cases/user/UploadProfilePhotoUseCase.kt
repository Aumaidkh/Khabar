package com.snapp.khabar.feature_fetch_news.domain.use_cases.user

import android.net.Uri
import com.snapp.khabar.feature_fetch_news.domain.repository.PhotoRepository
import javax.inject.Inject

class UploadProfilePhotoUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    suspend operator fun invoke(userId: String, imageUri: Uri): String? {
        return repository.uploadUserProfilePhoto(
            userId = userId,
            imageUri = imageUri
        )
    }
}