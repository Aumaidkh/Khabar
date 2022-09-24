package com.snapp.khabar.feature_fetch_news.domain.use_cases.user

import com.snapp.khabar.feature_fetch_news.data.remote.dto.UserDto
import com.snapp.khabar.feature_fetch_news.domain.repository.user.UserPreferencesDataStore
import javax.inject.Inject

class FetchUserFromDataStoreUseCase @Inject constructor(
    private val userPreferencesDataStore: UserPreferencesDataStore
) {

    suspend operator fun invoke(): UserDto {
        return userPreferencesDataStore.getProfileDetails()
    }
}