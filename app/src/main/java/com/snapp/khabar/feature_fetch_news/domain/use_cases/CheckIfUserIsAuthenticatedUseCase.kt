package com.snapp.khabar.feature_fetch_news.domain.use_cases

import com.snapp.khabar.feature_fetch_news.domain.repository.AuthRepository
import javax.inject.Inject

class CheckIfUserIsAuthenticatedUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke() =
        authRepository.isUserAuthenticated()
}