package com.snapp.khabar.feature_fetch_news.presentation.ui.sign_up

sealed class SignUpEvents{

    sealed class UserEvents{
        data class SignUpWithEmailAndPassword(
            val email: String,
            val password: String,
            val rePassword: String
        ): UserEvents()
        data class OnEmailChanged(
            val email: String
        ):UserEvents()

        data class OnPasswordChanged(
            val password: String
        ): UserEvents()

        data class OnReEnterPasswordChanged(
            val rePassword: String
        ): UserEvents()
    }

    sealed class UiEvents{
        object NavigateUserToHomeActivity: UiEvents()
        data class ShowSnackBar(
            val message: String
        ): UiEvents()
    }
}
