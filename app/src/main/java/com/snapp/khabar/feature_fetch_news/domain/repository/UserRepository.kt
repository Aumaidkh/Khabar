package com.snapp.khabar.feature_fetch_news.domain.repository

import com.snapp.khabar.feature_fetch_news.data.remote.dto.UserDto
import com.snapp.khabar.feature_fetch_news.data.util.UserResult
import com.snapp.khabar.feature_fetch_news.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun createUser(userDto: UserDto): Flow<UserResult>

    suspend fun getUserInfo(userId: String): UserDto?

    suspend fun updateUserInfo(userId: String): UserDto?
}