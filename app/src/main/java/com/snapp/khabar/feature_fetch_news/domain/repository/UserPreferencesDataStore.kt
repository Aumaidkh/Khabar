package com.snapp.khabar.feature_fetch_news.domain.repository

import com.snapp.khabar.feature_fetch_news.data.remote.dto.UserDto

interface UserPreferencesDataStore {

    suspend fun getProfileDetails(): UserDto

    suspend fun saveUserInfo(userDto: UserDto)
}