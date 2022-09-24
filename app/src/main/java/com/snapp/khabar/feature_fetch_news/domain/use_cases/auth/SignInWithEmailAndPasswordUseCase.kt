package com.snapp.khabar.feature_fetch_news.domain.use_cases.auth

import com.snapp.khabar.feature_fetch_news.domain.repository.EmailAndPasswordAuth
import javax.inject.Inject

class SignInWithEmailAndPasswordUseCase @Inject constructor(
    private val repository: EmailAndPasswordAuth
) {

    suspend operator fun invoke(email:String, password: String) =
        repository.signInWithEmailAndPassword(
            email = email,
            password = password
        )
}