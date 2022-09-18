package com.snapp.khabar.feature_fetch_news.domain.use_cases.user

import com.snapp.khabar.feature_fetch_news.data.local.DatastoreManager
import com.snapp.khabar.feature_fetch_news.data.remote.dto.UserDto
import com.snapp.khabar.feature_fetch_news.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val repository: UserRepository,
    private val datastoreManager: DatastoreManager
) {

    suspend fun execute(user: UserDto): Flow<UserDto>{
        // Save the data into datastore for the first place
        datastoreManager.saveUserInfo(user)
        return datastoreManager.getProfileDetails().onEach {
            repository.updateUserInfo(it.uid!!, it)
        }
    }

}