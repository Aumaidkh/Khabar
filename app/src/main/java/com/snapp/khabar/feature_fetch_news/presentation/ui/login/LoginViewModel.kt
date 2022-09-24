package com.snapp.khabar.feature_fetch_news.presentation.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.snapp.khabar.feature_fetch_news.data.remote.dto.UserDto
import com.snapp.khabar.feature_fetch_news.data.repository.AuthenticateUserWithGoogleUseCase
import com.snapp.khabar.feature_fetch_news.data.util.UserResult
import com.snapp.khabar.feature_fetch_news.domain.use_cases.auth.CheckIfUserIsAuthenticatedUseCase
import com.snapp.khabar.feature_fetch_news.domain.use_cases.auth.SignInWithEmailAndPasswordUseCase
import com.snapp.khabar.feature_fetch_news.domain.use_cases.user.SaveUserDataToDataStoreUseCase
import com.snapp.khabar.feature_fetch_news.domain.use_cases.user.SaveUserIntoFirestoreUseCase
import com.snapp.khabar.feature_fetch_news.domain.use_cases.validation.ValidationUseCases
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
    private val signInWithEmailAndPasswordUseCase: SignInWithEmailAndPasswordUseCase,
    private val validationUseCases: ValidationUseCases
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
             * On Email Changed
             * */
            is LoginEvents.OnEmailChanged -> {
                _state.update {
                    it.copy(
                        email = event.email
                    )
                }
                updateErrors()
            }

            /**
             * On Password Changed
             * */
            is LoginEvents.OnPasswordChanged -> {
                _state.update {
                    it.copy(
                        password = event.password
                    )
                }
                updateErrors()
            }

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

            /**
             * Navigating user to sign up screen
             * */
            is LoginEvents.SignUpClick -> {
                viewModelScope.launch {
                    _eventFlow.emit(
                        LoginUiEvents.NavigateToSignUpScreen
                    )
                }
            }

            /**
             * Logging in with email and password
             * */
            is LoginEvents.LoginWithEmailAndPassword -> {
                validateFields(
                    email = _state.value.email,
                    password = _state.value.password
                )
            }
        }
    }

    private fun validateFields(email: String,password: String){
        val emailResult = validationUseCases.validateEmailUseCase.execute(email)
        val passwordResult = validationUseCases.validatePasswordUseCase.invoke(password,password)

        val fieldHasError = listOf(
            emailResult,
            passwordResult
        ).any {
            !it.successful
        }

        if (fieldHasError){
            return
        }

        signInUserWithEmailAndPassword(email, password)
    }

    private fun signInUserWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            signInWithEmailAndPasswordUseCase.invoke(
                email = email,
                password = password
            ).onEach { result ->
                when (result) {
                    is Result.Loading -> {
                        _state.update {
                            it.copy(isLoading = true, isAuthenticated = false)
                        }
                    }

                    is Result.Success -> {
                        _state.update {
                            it.copy(isLoading = false, isAuthenticated = true)
                        }
                        // Save the user data in datastore here
                        saveUserDataToDataStoreUseCase.invoke(result.data!!)
                    }

                    is Result.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false, isAuthenticated = false
                            )
                        }
                        _eventFlow.emit(LoginUiEvents.ShowSnackBar(result.message.toString()))
                    }
                }

            }.launchIn(this)
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
                        Log.d(TAG, "ifUserAlreadyAuthenticated: Loading")
                    }

                    is Result.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isAuthenticated = result.data ?: false
                            )
                        }
                        Log.d(TAG, "ifUserAlreadyAuthenticated: Success")
                    }

                    is Result.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isAuthenticated = false
                            )
                        }

                        Log.d(TAG, "ifUserAlreadyAuthenticated: Error")

                        _eventFlow.emit(LoginUiEvents.ShowSnackBar(result.message.toString()))
                    }
                }
            }.launchIn(this)
        }
    }


    /**
     * Update Errors if there are any
     * */
    private fun updateErrors(){

        /**
         * Re validating Email*/
        validationUseCases.validateEmailUseCase.execute(
            _state.value.email
        ).also { result ->
            _state.update {
                it.copy(
                    emailError = result.errorMessage
                )
            }
        }

        /**
         * Re Validating Password*/
        validationUseCases.validatePasswordUseCase.invoke(
            _state.value.password,
            _state.value.password
        ).also { result ->
            _state.update {
                it.copy(
                    passwordError = result.errorMessage
                )
            }
        }


    }

}

private const val TAG = "LoginViewModel"