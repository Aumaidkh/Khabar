package com.snapp.khabar.feature_fetch_news.data.repository.auth

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.snapp.khabar.feature_fetch_news.data.remote.dto.UserDto
import com.snapp.khabar.feature_fetch_news.domain.repository.auth.EmailAndPasswordAuth
import com.snapp.khabar.feature_fetch_news.domain.util.Result
import com.snapp.khabar.feature_fetch_news.domain.util.toUserDto
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

class EmailAndPasswordAuthImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): EmailAndPasswordAuth {

    /**
     * Signs up the user with email and password and returns a userDto
     * containing all the information
     * */
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Result<UserDto?>> {
        return callbackFlow<Result<UserDto?>> {
            val onSuccessListener = OnSuccessListener<AuthResult> { result ->
                launch {
                    send(Result.Success(result.user.toUserDto()))
                }
            }

            val onFailureListener = OnFailureListener {
                launch {
                    send(Result.Error(it.message.toString()))
                }
            }

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener)
            awaitClose()
        }.distinctUntilChanged()
    }

    /**
     * Sign in a user with email and password and return a user out of it
     * */
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Result<UserDto?>> {
        return callbackFlow<Result<UserDto?>> {
            val onSuccess = OnSuccessListener<AuthResult> { authResult ->
                launch {
                    send(Result.Success(authResult.user.toUserDto()))
                }
            }

            val onFailure = OnFailureListener {
                launch {
                    send(Result.Error(it.message.toString()))
                }
            }

            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(onSuccess)
                .addOnFailureListener(onFailure)
            awaitClose()
        }.distinctUntilChanged()
    }


    /**
     * Sign out a user
     * */
    override suspend fun signOut(){
        firebaseAuth.signOut()
    }
}