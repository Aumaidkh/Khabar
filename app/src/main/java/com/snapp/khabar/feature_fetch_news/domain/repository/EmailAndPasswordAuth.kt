package com.snapp.khabar.feature_fetch_news.domain.repository

import com.snapp.khabar.feature_fetch_news.data.remote.dto.UserDto
import com.snapp.khabar.feature_fetch_news.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface EmailAndPasswordAuth {

    suspend fun signUpWithEmailAndPassword(email: String, password: String): Flow<Result<UserDto?>>

    suspend fun signInWithEmailAndPassword(email: String, password: String): Flow<Result<UserDto?>>

    suspend fun signOut()
}