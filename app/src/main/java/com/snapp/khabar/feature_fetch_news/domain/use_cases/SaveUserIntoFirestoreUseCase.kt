package com.snapp.khabar.feature_fetch_news.domain.use_cases

import com.snapp.khabar.feature_fetch_news.data.remote.dto.UserDto
import com.snapp.khabar.feature_fetch_news.data.util.UserResult
import com.snapp.khabar.feature_fetch_news.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveUserIntoFirestoreUseCase @Inject constructor(
    private val userRepository: UserRepository
){

    suspend operator fun invoke(userDto: UserDto): Flow<UserResult> {
        return userRepository.createUser(userDto)
    }
}