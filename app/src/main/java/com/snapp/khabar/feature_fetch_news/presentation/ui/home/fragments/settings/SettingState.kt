package com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.settings

data class SettingState(
    val username: String = "",
    val email: String = "",
    val imageUrl: String = "",
    val darkModeEnabled: Boolean = false,
    val notificationEnabled: Boolean = false
)
