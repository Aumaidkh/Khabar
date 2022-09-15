package com.snapp.khabar.feature_fetch_news.data.repository

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.snapp.khabar.feature_fetch_news.data.remote.dto.UserDto
import com.snapp.khabar.feature_fetch_news.domain.repository.AuthRepository
import com.snapp.khabar.feature_fetch_news.domain.util.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {


    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun authenticate(authCredential: AuthCredential): Flow<Result<UserDto>> {
        return callbackFlow<Result<UserDto>> {
            launch {
                send(Result.Loading())
            }
            val onSuccessListener = OnSuccessListener<AuthResult>{ authResult ->
                val firebaseUser = authResult.user
                if (firebaseUser != null) {
                    launch {
                        send(
                            Result.Success(
                                UserDto(
                                    uid = firebaseUser.uid,
                                    name = firebaseUser.displayName,
                                    email = firebaseUser.email,
                                    phoneNumber = firebaseUser.phoneNumber,
                                    photoUrl = firebaseUser.photoUrl.toString()
                                )
                            )
                        )
                    }
                }
            }

            val onFailureListener = OnFailureListener{
                launch {
                    send(
                        Result.Error(it.message.toString())
                    )
                }
            }

            firebaseAuth.signInWithCredential(authCredential)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener)

            awaitClose()
        }.distinctUntilChanged()
    }
}
