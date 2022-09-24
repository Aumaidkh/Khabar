package com.snapp.khabar.feature_fetch_news.domain.use_cases.auth

import com.snapp.khabar.feature_fetch_news.domain.repository.EmailAndPasswordAuth
import javax.inject.Inject

class CreateUserWithEmailAndPasswordUseCase @Inject constructor(
    private val repository: EmailAndPasswordAuth
) {

    suspend operator fun invoke(email: String, password: String) =
        repository.signUpWithEmailAndPassword(email, password)
}