package com.snapp.khabar.feature_fetch_news.presentation.ui.login

data class LoginScreenState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null
)
