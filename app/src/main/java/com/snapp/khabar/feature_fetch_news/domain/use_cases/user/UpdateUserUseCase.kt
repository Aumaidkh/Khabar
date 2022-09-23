package com.snapp.khabar.feature_fetch_news.domain.use_cases.user

import android.util.Log
import com.snapp.khabar.feature_fetch_news.data.remote.dto.UserDto
import com.snapp.khabar.feature_fetch_news.data.util.UserResult
import com.snapp.khabar.feature_fetch_news.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val TAG = "UpdateUserUseCase"

class UpdateUserUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend fun execute(user: UserDto): Flow<UserResult> {
        // Save the data into datastore for the first place
        Log.d(TAG, "User: $user")
        return repository.updateUserInfo(user.uid!!, user)
    }

}