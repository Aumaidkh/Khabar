package com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.settings

import com.snapp.khabar.feature_fetch_news.data.remote.dto.UserDto

data class SettingState(
    val isLoading: Boolean = true,
    val userId: String = "",
    val username: String = "",
    val email: String = "",
    val imageUrl: String = "",
    val phoneNumber: String? = null,
    val darkModeEnabled: Boolean = false,
    val notificationEnabled: Boolean = false
)

fun SettingState.toUserDto() =
    UserDto(
        uid = userId,
        name = username,
        email = email,
        photoUrl = imageUrl,
        phoneNumber = phoneNumber
    )
