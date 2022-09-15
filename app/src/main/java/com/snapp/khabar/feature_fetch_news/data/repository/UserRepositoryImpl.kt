package com.snapp.khabar.feature_fetch_news.data.repository

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.snapp.khabar.feature_fetch_news.data.remote.dto.UserDto
import com.snapp.khabar.feature_fetch_news.domain.repository.UserRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : UserRepository {

    override suspend fun signInWithGoogle(authCredential: AuthCredential): UserDto? {
        firebaseAuth.signInWithCredential(authCredential).await().also { result ->
            return if (result.user != null){
                UserDto(
                    uid = result.user!!.uid,
                    name = result.user!!.displayName,
                    email = result.user!!.email,
                    phoneNumber = result.user!!.phoneNumber,
                    photoUrl = result.user!!.photoUrl.toString()
                )
            } else {
                null
            }
        }
    }
}