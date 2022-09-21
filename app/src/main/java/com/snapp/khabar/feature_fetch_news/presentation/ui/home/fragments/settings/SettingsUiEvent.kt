package com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.settings

import com.snapp.khabar.feature_fetch_news.core.Constants.DEFAULT_EMAIL_ADDRESS
import com.snapp.khabar.feature_fetch_news.core.Constants.DEFAULT_PHONE_NUMBER
import com.snapp.khabar.feature_fetch_news.core.Constants.PRIVACY_POLICY_URL
import com.snapp.khabar.feature_fetch_news.domain.model.UserSettings

sealed class SettingsUiEvent{
    data class ShowSnackBar(val message: String): SettingsUiEvent()
    object NavigateToEditProfileScreen: SettingsUiEvent()
    object NavigateUserToLoginScreen: SettingsUiEvent()
    data class PrivacyPolicyEvent(
        val url: String = PRIVACY_POLICY_URL
    ): SettingsUiEvent()
    data class EmailEvent(
        val email: String = DEFAULT_EMAIL_ADDRESS
    ): SettingsUiEvent()

    data class PhoneEvent(
        val phone: String = DEFAULT_PHONE_NUMBER
    ): SettingsUiEvent()

    data class DarkModeToggle(
        val isEnabled: Boolean
    ): SettingsUiEvent()

    data class NotificationToggle(
        val isEnabled: Boolean
    ): SettingsUiEvent()

    data class UserSetting(
        val settings: UserSettings
    ): SettingsUiEvent()
}
