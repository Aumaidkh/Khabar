package com.snapp.khabar.feature_fetch_news.domain.use_cases.auth

import com.snapp.khabar.feature_fetch_news.domain.repository.AuthRepository
import javax.inject.Inject

class CreateUserWithEmailAndPasswordUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(email: String, password: String) =
        repository.createUserWithEmailAndPassword(email, password)
}