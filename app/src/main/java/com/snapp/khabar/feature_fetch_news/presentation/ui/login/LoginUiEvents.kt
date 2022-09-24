package com.snapp.khabar.feature_fetch_news.presentation.ui.login

/**
 * All set of events login viewModel sends back to the login screen
 * */
sealed class LoginUiEvents{
    /**
     * Used to show snack bar with the message attached to it
     * */
    data class ShowSnackBar(val message: String): LoginUiEvents()

    /**
     * Sent once sign up click is received
     * */
    object NavigateToSignUpScreen: LoginUiEvents()
}
