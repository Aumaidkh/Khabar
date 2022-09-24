package com.snapp.khabar.feature_fetch_news.domain.use_cases.auth

import com.google.firebase.auth.AuthCredential
import com.snapp.khabar.feature_fetch_news.data.remote.dto.UserDto
import com.snapp.khabar.feature_fetch_news.domain.repository.auth.AuthRepository
import com.snapp.khabar.feature_fetch_news.domain.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthenticateUserWithGoogleUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(authCredential: AuthCredential) : Flow<Result<UserDto>>{

        return authRepository.authenticate(authCredential)
    }
}