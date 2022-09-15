package com.snapp.khabar.feature_fetch_news.data.util

sealed class UserResult {

    object Progress : UserResult()

    sealed class Complete : UserResult() {
        object Success: Complete()
        data class Failed(val error: String) : Complete()
    }
}