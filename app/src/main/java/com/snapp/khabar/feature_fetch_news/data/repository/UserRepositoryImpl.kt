package com.snapp.khabar.feature_fetch_news.data.repository

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.OnProgressListener
import com.snapp.khabar.feature_fetch_news.data.local.DatastoreManager
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
    private val firestore: FirebaseFirestore,
    private val datastoreManager: DatastoreManager
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
                .document(userDto.uid!!)
                .set(userDto)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener)



            awaitClose()
        }.distinctUntilChanged()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getUserInfo(userId: String): Flow<UserDto?> {
        return callbackFlow {
            val onSuccess = OnSuccessListener<DocumentSnapshot> { result ->
                launch {
                    val user = UserDto(
                        uid = result.getString("uid"),
                        name = result.getString("name"),
                        email = result.getString("email"),
                        photoUrl = result.getString("photoUrl"),
                        phoneNumber = result.getString("phoneNumber")
                    )
                    send(user)

                    /**
                     * Making Sure we are updating data store after getting the latest data
                     * */
                    datastoreManager.saveUserInfo(user)
                }
            }

            val onFailure = OnFailureListener { exception ->
                launch {
                    send(null)
                }
            }

            firestore
                .collection(USERS_COLLECTION)
                .document(userId)
                .get()
                .addOnSuccessListener(onSuccess)
                .addOnFailureListener(onFailure)

            awaitClose()
        }.distinctUntilChanged()
    }

    override suspend fun updateUserInfo(userId: String, userDto: UserDto): Flow<UserResult> {
        return callbackFlow {
            val onSuccess = OnSuccessListener<Void>{ result ->
                launch {
                    send(
                        UserResult.Complete.Success
                    )
                }
            }


            val onFailure = OnFailureListener {
                launch {
                    send(
                        UserResult.Complete.Failed(it.message.toString())
                    )
                }
            }

            firestore
                .collection(USERS_COLLECTION)
                .document(userId)
                .set(userDto)
                .addOnSuccessListener(onSuccess)
                .addOnFailureListener(onFailure)

            /**
             * Updating the datastore preferences as well
             * */
            datastoreManager.saveUserInfo(userDto)
        }
    }


}