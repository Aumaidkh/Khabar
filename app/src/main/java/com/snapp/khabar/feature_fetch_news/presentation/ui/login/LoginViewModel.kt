package com.snapp.khabar.feature_fetch_news.presentation.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.snapp.khabar.feature_fetch_news.data.remote.dto.UserDto
import com.snapp.khabar.feature_fetch_news.data.repository.auth.AuthenticateUserWithGoogleUseCase
import com.snapp.khabar.feature_fetch_news.data.util.UserResult
import com.snapp.khabar.feature_fetch_news.domain.use_cases.auth.CheckIfUserIsAuthenticatedUseCase
import com.snapp.khabar.feature_fetch_news.domain.use_cases.user.SaveUserDataToDataStoreUseCase
import com.snapp.khabar.feature_fetch_news.domain.use_cases.user.SaveUserIntoFirestoreUseCase
import com.snapp.khabar.feature_fetch_news.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticateUserWithGoogleUseCase: AuthenticateUserWithGoogleUseCase,
    private val saveUserIntoFirestoreUseCase: SaveUserIntoFirestoreUseCase,
    private val isUserAuthenticatedUseCase: CheckIfUserIsAuthenticatedUseCase,
    private val saveUserDataToDataStoreUseCase: SaveUserDataToDataStoreUseCase,
) : ViewModel() {

    /**
     * Login with google state
     * */
    private val _state = MutableStateFlow(LoginScreenState())
    val state = _state.asStateFlow()

    /**
     * Sending Ui Events
     * */
    private val _eventFlow = MutableSharedFlow<LoginUiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()


    fun onEvent(event: LoginEvents) {
        when (event) {

            /**
             * Login in the user through google */
            is LoginEvents.Login -> {
                signInWithGoogle(event.authCredential)
            }

            /**
             * Checking if user is already logged in
             * */
            is LoginEvents.CheckIfUserIsAlreadyAuthenticated -> {
                ifUserAlreadyAuthenticated()
            }
        }
    }


    /**
     * Signs in the user with the auth credentials
     * */
    private fun signInWithGoogle(authCredential: AuthCredential) {
        viewModelScope.launch {
            authenticateUserWithGoogleUseCase.invoke(authCredential).onEach { result ->
                when (result) {
                    is Result.Loading -> {
                        _state.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Result.Error -> {
                        _state.update {
                            it.copy(isLoading = false, isAuthenticated = false)
                        }

                        _eventFlow.emit(LoginUiEvents.ShowSnackBar(result.message.toString()))
                    }

                    is Result.Success -> {
                        _state.update {
                            it.copy(isLoading = false, isAuthenticated = true)
                        }
                        saveUserToFirestore(result.data!!)
                        saveUserToDataStore(result.data)

                    }
                }
            }.launchIn(this)
        }
    }

    /**
     * Saving User Data to the local datastore*/
    private fun saveUserToDataStore(data: UserDto) {
        viewModelScope.launch {
            saveUserDataToDataStoreUseCase.invoke(userDto = data)
        }
    }


    /**
     * Saves the user to the fire store
     * */
    private suspend fun saveUserToFirestore(userModel: UserDto){
        saveUserIntoFirestoreUseCase.invoke(userModel).onEach { userResult ->
            when(userResult){
                is UserResult.Progress -> {
                    Log.d(TAG, "saveUser: Saving")
                    _state.update {
                        it.copy(isLoading = true)
                    }
                }

                is UserResult.Complete.Success -> {
                    _state.update {
                        it.copy(isLoading = false)
                    }
                    Log.d(TAG, "saveUser: Saved")
                }

                is UserResult.Complete.Failed -> {
                    Log.d(TAG, "saveUser: Failed")
                    _state.update {
                        it.copy(isLoading = false)
                    }
                    _eventFlow.emit(LoginUiEvents.ShowSnackBar(userResult.error))
                }
            }
        }.launchIn(viewModelScope)
    }


    /**
     * Checking if user is authenticated
     * */
    private fun ifUserAlreadyAuthenticated(){
        viewModelScope.launch {
            isUserAuthenticatedUseCase.invoke().onEach { result ->
                when(result){
                    is Result.Loading -> {
                        _state.update {
                            it.copy(isLoading = true, isAuthenticated = false)
                        }
                    }

                    is Result.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                        _eventFlow.emit(LoginUiEvents.UserAuthenticated)
                    }

                    is Result.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false
                            )
                        }

                        _eventFlow.emit(LoginUiEvents.UserNotAuthenticated)

                        _eventFlow.emit(LoginUiEvents.ShowSnackBar(result.message.toString()))
                    }
                }
            }.launchIn(this)
        }
    }

}

private const val TAG = "LoginViewModel"