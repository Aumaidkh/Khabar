package com.snapp.khabar.feature_fetch_news.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserSettings(
    val isDarkModeEnabled: Boolean = false,
    val isNotificationEnabled: Boolean= false
)
