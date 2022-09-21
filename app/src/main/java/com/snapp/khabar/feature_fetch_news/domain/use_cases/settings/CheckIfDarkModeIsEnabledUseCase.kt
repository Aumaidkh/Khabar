package com.snapp.khabar.feature_fetch_news.domain.use_cases.settings

import com.snapp.khabar.feature_fetch_news.data.local.UserSettingsDatastoreManager
import javax.inject.Inject

class CheckIfDarkModeIsEnabledUseCase @Inject constructor(
    private val userSettingsDatastoreManager: UserSettingsDatastoreManager
) {

    suspend operator fun invoke() =
        userSettingsDatastoreManager.isDarkModeEnabled()
}