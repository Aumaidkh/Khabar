package com.snapp.khabar.feature_fetch_news.presentation.ui.login

import android.util.Log
import com.google.firebase.auth.AuthCredential

sealed class LoginEvents{
    data class Login(val authCredential: AuthCredential): LoginEvents()

    /**
     * Will be used to send check authentication event
     * */
    object CheckIfUserIsAlreadyAuthenticated: LoginEvents()

    object SignUpClick: LoginEvents()

    object LoginWithEmailAndPassword: LoginEvents()

    data class OnEmailChanged(
        val email: String
    ): LoginEvents()

    data class OnPasswordChanged(
        val password: String
    ): LoginEvents()
}
