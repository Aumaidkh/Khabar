package com.snapp.khabar.feature_fetch_news.data.repository

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import com.snapp.khabar.feature_fetch_news.data.remote.dto.UserDto
import com.snapp.khabar.feature_fetch_news.data.util.Constants.USERS_COLLECTION
import com.snapp.khabar.feature_fetch_news.data.util.UserResult
import com.snapp.khabar.feature_fetch_news.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : UserRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun createUser(userDto: UserDto): Flow<UserResult> {
        return callbackFlow<UserResult> {

            val onFailureListener = OnFailureListener{
                launch {
                    send(UserResult.Complete.Failed(it.message.toString()))
                }
            }


            val onSuccessListener = OnSuccessListener<Void>{ result ->
                launch {
                    send(UserResult.Complete.Success)
                }
            }


            firestore
                .collection(USERS_COLLECTION)
                .document(userDto.uid)
                .set(userDto)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener)



            awaitClose()
        }.distinctUntilChanged()
    }

    override suspend fun getUserInfo(userId: String): UserDto? {
        return null
    }

    override suspend fun updateUserInfo(userId: String): UserDto? {
        return null
    }


}