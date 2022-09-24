package com.snapp.khabar.feature_fetch_news.presentation.ui.sign_up


/**
 * All set of events related to the sign up screen
 * */
sealed class SignUpEvents{

    /**
     * These events will be sent from user ( screen ) to the viewModel
     * */
    sealed class UserEvents{

        /**
         * Signs up the user with email and password
         * */
        data class SignUpWithEmailAndPassword(
            val email: String,
            val password: String,
            val rePassword: String
        ): UserEvents()

        /**
         * Invoked once the text in email input field changes
         * Updates the state of email field accordingly
         * */
        data class OnEmailChanged(
            val email: String
        ):UserEvents()

        /**
         * Invoked once the text in password field changes
         * Updates the state of password field accordingly*/
        data class OnPasswordChanged(
            val password: String
        ): UserEvents()

        /**
         * Invoked once the text in re-enter password field changes
         * Updates the state of re-enter password field accordingly*/
        data class OnReEnterPasswordChanged(
            val rePassword: String
        ): UserEvents()
    }

    /**
     * These set of events are sent from viewModel back to screen
     * */
    sealed class UiEvents{
        /**
         * Navigating the user to the homeActivity
         * Like once a user successfully signs himself up*/
        object NavigateUserToHomeActivity: UiEvents()

        /**
         * Shows a snack bar with the message attached
         * to the event
         * */
        data class ShowSnackBar(
            val message: String
        ): UiEvents()
    }
}
