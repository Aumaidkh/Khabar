package com.snapp.khabar.feature_fetch_news.data.repository

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.snapp.khabar.feature_fetch_news.domain.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke() =
        authRepository.signOut()
}