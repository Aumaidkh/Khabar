package com.snapp.khabar.feature_fetch_news.domain.use_cases.auth

import javax.inject.Inject

data class AuthUseCases @Inject constructor(
    val authenticateUserWithGoogle: AuthenticateUserWithGoogleUseCase,
    val checkIfUserIsAuthenticated: CheckIfUserIsAuthenticatedUseCase,
    val createUserWithEmailAndPassword: CreateUserWithEmailAndPasswordUseCase,
    val signInWithEmailAndPassword: SignInWithEmailAndPasswordUseCase,
    val signOut: SignOutUseCase
)