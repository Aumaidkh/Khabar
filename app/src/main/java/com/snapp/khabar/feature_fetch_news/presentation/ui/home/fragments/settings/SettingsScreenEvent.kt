package com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.settings

sealed class SettingsScreenEvent {
    data class DarkModeToggle( val enabled: Boolean): SettingsScreenEvent()
    data class NotificationToggle(val enabled: Boolean): SettingsScreenEvent()
    object EditProfile: SettingsScreenEvent()
    object SignOut: SettingsScreenEvent()
    object InitScreenState: SettingsScreenEvent()
    object PrivacyPolicyClickEvent: SettingsScreenEvent()
    object EmailClickEvent: SettingsScreenEvent()
    object PhoneNumberClickEvent: SettingsScreenEvent()
    object IsDarkModeEnabled: SettingsScreenEvent()
    object IsNotificationsEnabled: SettingsScreenEvent()
    data class ApplyDarkMode(
        val isEnabled: Boolean
    ): SettingsScreenEvent()
    data class EnableNotification(
        val isEnabled: Boolean
    ): SettingsScreenEvent()

}