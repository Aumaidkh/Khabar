package com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.settings

import com.snapp.khabar.feature_fetch_news.data.remote.dto.UserDto

sealed class SettingsUiEvent{
    data class ShowSnackBar(val message: String): SettingsUiEvent()
    data class NavigateToEditProfileScreen(
        val userDto: UserDto
    ): SettingsUiEvent()
}
