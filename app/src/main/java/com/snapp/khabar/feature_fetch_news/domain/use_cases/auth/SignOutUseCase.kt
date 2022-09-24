package com.snapp.khabar.feature_fetch_news.domain.use_cases.auth

import com.snapp.khabar.feature_fetch_news.domain.repository.auth.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke() =
        authRepository.signOut()
}