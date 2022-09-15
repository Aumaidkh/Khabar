package com.snapp.khabar.feature_fetch_news.domain.repository

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthCredential
import com.snapp.khabar.feature_fetch_news.data.remote.dto.UserDto
import com.snapp.khabar.feature_fetch_news.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun authenticate(authCredential: AuthCredential): Flow<Result<UserDto>>

    suspend fun isUserAuthenticated(): Flow<Result<Boolean>>

    suspend fun signOut()
}