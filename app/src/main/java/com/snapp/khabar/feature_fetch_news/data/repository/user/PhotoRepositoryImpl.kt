package com.snapp.khabar.feature_fetch_news.data.repository.user

import android.net.Uri
import com.google.firebase.storage.StorageReference
import com.snapp.khabar.feature_fetch_news.domain.repository.PhotoRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class PhotoRepositoryImpl @Inject constructor(
    private val storageRef: StorageReference
): PhotoRepository {

    override suspend fun uploadUserProfilePhoto(userId: String, imageUri: Uri): String? {
        val newsImageRef = storageRef.child("images/profile_photos/$userId.png")
        val imageUrl: String?
        newsImageRef.putFile(imageUri).await()
        imageUrl = newsImageRef.downloadUrl.await().toString()
        return imageUrl
    }
}