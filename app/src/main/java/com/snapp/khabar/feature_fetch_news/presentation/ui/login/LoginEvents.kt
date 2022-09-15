package com.snapp.khabar.feature_fetch_news.presentation.ui.login

import com.google.firebase.auth.AuthCredential

sealed class LoginEvents{
    data class Login(val authCredential: AuthCredential): LoginEvents()
}
