package com.snapp.khabar.feature_fetch_news.presentation.ui.login

import com.google.firebase.auth.AuthCredential

/**
 * All set of events login screen sends to the viewModel
 * */
sealed class LoginEvents{

    /**
     * Logs in a user with the auth credential got from google sign in
     * client
     * */
    data class Login(val authCredential: AuthCredential): LoginEvents()

    /**
     * Sent to check if a user is already logged in
     * */
    object CheckIfUserIsAlreadyAuthenticated: LoginEvents()

    /**
     * Sent when sign up text button is clicked in ui which then triggers
     * navigate to sign up screen event in viewModel
     * */
    object SignUpClick: LoginEvents()

    /**
     * Sent to start log in process
     * value of email and password are collecting from the state itself
     * then validated and then a user in signed in
     * */
    object LoginWithEmailAndPassword: LoginEvents()

    /**
     * Sent when any char in email input field changes and state is updated immediately
     * */
    data class OnEmailChanged(
        val email: String
    ): LoginEvents()

    /**
     * Sent when any char in password input field changes and state is updated immediately
     * */
    data class OnPasswordChanged(
        val password: String
    ): LoginEvents()
}
