package com.snapp.khabar.feature_fetch_news.presentation.ui.login

sealed class LoginUiEvents{
    object Empty: LoginUiEvents()
    data class ShowSnackBar(val message: String): LoginUiEvents()
    object N
}
