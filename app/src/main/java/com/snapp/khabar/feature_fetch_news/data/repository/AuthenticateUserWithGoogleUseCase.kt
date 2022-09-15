package com.snapp.khabar.feature_fetch_news.data.repository

import com.google.firebase.auth.AuthCredential
import com.snapp.khabar.feature_fetch_news.data.remote.dto.UserDto
import com.snapp.khabar.feature_fetch_news.domain.mappers.toUserModel
import com.snapp.khabar.feature_fetch_news.domain.model.UserModel
import com.snapp.khabar.feature_fetch_news.domain.repository.AuthRepository
import com.snapp.khabar.feature_fetch_news.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthenticateUserWithGoogleUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(authCredential: AuthCredential) : Flow<Result<UserDto>>{

        return authRepository.authenticate(authCredential)
    }
}