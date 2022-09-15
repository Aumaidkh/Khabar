package com.snapp.khabar.feature_fetch_news.domain.repository

import com.google.firebase.auth.AuthCredential
import com.snapp.khabar.feature_fetch_news.data.remote.dto.UserDto

interface UserRepository {

    suspend fun signInWithGoogle(authCredential: AuthCredential): UserDto?
}