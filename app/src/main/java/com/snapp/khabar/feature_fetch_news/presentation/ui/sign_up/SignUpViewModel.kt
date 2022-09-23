package com.snapp.khabar.feature_fetch_news.presentation.ui.sign_up

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snapp.khabar.feature_fetch_news.domain.repository.UserPreferencesDataStore
import com.snapp.khabar.feature_fetch_news.domain.use_cases.auth.CreateUserWithEmailAndPasswordUseCase
import com.snapp.khabar.feature_fetch_news.domain.use_cases.validation.ValidationUseCases
import com.snapp.khabar.feature_fetch_news.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SignUpViewModel"
@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val createUserWithEmailAndPasswordUseCase: CreateUserWithEmailAndPasswordUseCase,
    private val validationUseCases: ValidationUseCases,
    private val userPreferencesDataStore: UserPreferencesDataStore
): ViewModel() {

    /**
     * Sign Up Screen State
     * */
    private val _state = MutableStateFlow(SignUpState())
    val state = _state.asStateFlow()

    /**
     * Event Flow
     * */
    private val _eventChannel: Channel<SignUpEvents.UiEvents> = Channel()
    val eventFlow = _eventChannel.receiveAsFlow()

    /**
     * Accepting Events
     * */
    fun onEvent(event: SignUpEvents.UserEvents){
        when(event){
            is SignUpEvents.UserEvents.SignUpWithEmailAndPassword -> {
                validateFields(
                    email = event.email,
                    password = event.password,
                    rePassword = event.rePassword
                )
            }

            is SignUpEvents.UserEvents.OnEmailChanged -> {
                _state.update {
                    it.copy(email = event.email)
                }
                updateErrors()
            }

            is SignUpEvents.UserEvents.OnPasswordChanged -> {
                _state.update {
                    it.copy(
                        password = event.password
                    )
                }
                updateErrors()
            }

            is SignUpEvents.UserEvents.OnReEnterPasswordChanged -> {
                _state.update {
                    it.copy(
                        rePassword = event.rePassword
                    )
                }
                updateErrors()
            }
        }
    }


    /**
     * Validating email and password fields
     * if fields are valid create a user with email and password then
     * */
    private fun validateFields(email: String, password: String, rePassword: String){
        val emailResult = validationUseCases.validateEmailUseCase.execute(email)
        val passwordResult = validationUseCases.validatePasswordUseCase.invoke(password, rePassword)

        val fieldHasError = listOf(
            emailResult,
            passwordResult
        ).any {
            !it.successful
        }

        if (fieldHasError){
            return
        }

        viewModelScope.launch {
            createUserWithEmailAndPassword(email, password)
        }
    }


    /**
     * Requests firebase to create user with email and password once
     * all the fields are validated successfully
     * */
    private suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ) {
        createUserWithEmailAndPasswordUseCase.invoke(
            email = email,
            password = password
        ).onEach { result ->

            when (result) {
                is Result.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = true,
                            isUserAuthenticated = false
                        )
                    }
                }

                is Result.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isUserAuthenticated = true
                        )
                    }
                    // Save Data to the preferences now
                    userPreferencesDataStore.saveUserInfo(result.data!!)

                    // Emit Success Event
                    _eventChannel.send(
                        SignUpEvents.UiEvents.NavigateUserToHomeActivity
                    )
                }

                is Result.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isUserAuthenticated = false
                        )
                    }

                    _eventChannel.send(
                        SignUpEvents.UiEvents.ShowSnackBar(
                            message = result.message.toString()
                        )
                    )
                }
            }

        }.launchIn(viewModelScope)
    }

    /**
     * Updates Error if there are any
     * */
    private fun updateErrors(){
        validationUseCases.validateEmailUseCase.execute(_state.value.email).also { result ->
            _state.update {
                it.copy(
                    emailError = result.errorMessage
                )
            }
        }
       validationUseCases.validatePasswordUseCase.invoke(_state.value.password, _state.value.rePassword).also { result ->
           _state.update {
               it.copy(
                   passwordError = result.errorMessage
               )
           }
       }
    }
}