package com.snapp.khabar.feature_fetch_news.presentation.ui.sign_up

import com.snapp.khabar.feature_fetch_news.domain.util.ValidationResult

data class SignUpState(
    val isLoading: Boolean? = null,
    val isUserAuthenticated: Boolean = false,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val rePassword: String = "",
    val rePasswordError: String? = null
)
