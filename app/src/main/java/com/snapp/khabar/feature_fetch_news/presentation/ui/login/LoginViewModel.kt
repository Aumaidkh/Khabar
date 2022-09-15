package com.snapp.khabar.feature_fetch_news.presentation.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.snapp.khabar.feature_fetch_news.data.remote.dto.UserDto
import com.snapp.khabar.feature_fetch_news.data.repository.AuthenticateUserWithGoogleUseCase
import com.snapp.khabar.feature_fetch_news.domain.model.UserModel
import com.snapp.khabar.feature_fetch_news.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticateUserWithGoogleUseCase: AuthenticateUserWithGoogleUseCase
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
            is LoginEvents.Login -> {
                signInWithGoogle(event.authCredential)
            }
        }
    }


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

                    }
                }
            }.launchIn(this)
        }
    }

    private fun saveUser(userModel: UserModel){

    }

}

private const val TAG = "LoginViewModel"