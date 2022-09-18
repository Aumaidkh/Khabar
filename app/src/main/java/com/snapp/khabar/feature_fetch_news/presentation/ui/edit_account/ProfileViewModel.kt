package com.snapp.khabar.feature_fetch_news.presentation.ui.edit_account

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snapp.khabar.feature_fetch_news.domain.use_cases.user.UpdateUserUseCase
import com.snapp.khabar.feature_fetch_news.domain.use_cases.validation.ValidationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "ProfileViewModel"

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val validationUseCases: ValidationUseCases,
    private val updateUserUseCase: UpdateUserUseCase
): ViewModel() {

    /**
     * Edit Profile State
     * */
    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    /**
     * Ui Event Flow
     * */
    private val _eventFlow = MutableSharedFlow<EditProfileEvents.UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()


    fun onEvent(event: EditProfileEvents){
        when(event){
            is EditProfileEvents.OnNameChange -> {
                _state.update {
                    it.copy(
                        name = event.name
                    )
                }
                nullifyErrorsIfAny(NAME)
            }
            is EditProfileEvents.OnGenderChange -> {
                _state.update {
                    it.copy(
                        gender = event.gender
                    )
                }
            }
            is EditProfileEvents.OnEmailChange -> {
                _state.update {
                    it.copy(
                        email = event.email
                    )
                }
                nullifyErrorsIfAny(EMAIL)
            }
            is EditProfileEvents.OnPhoneChanged -> {
                _state.update {
                    it.copy(
                        phone = event.phone
                    )
                }
                nullifyErrorsIfAny(PHONE)
            }

            is EditProfileEvents.OnProfilePicChanged -> {
                _state.update {
                    it.copy(
                        imageUri = event.imageUri
                    )
                }
            }

            is EditProfileEvents.SaveChanges -> {
                saveChanges()
            }
        }
    }

    private fun saveChanges(){
        val nameResult = validationUseCases.validateNameUseCase.execute(_state.value.name)
        val emailResult = validationUseCases.validateNameUseCase.execute(_state.value.email)
        val phoneResult = validationUseCases.validateNameUseCase.execute(_state.value.phone)

        val anyFieldHasError = listOf(
            nameResult,
            emailResult,
            phoneResult
        ).any {
            !it.successful
        }

        if (anyFieldHasError){
            Log.d(TAG, "Field has error")
            _state.update {
                it.copy(
                    nameError = nameResult.errorMessage,
                    emailError = emailResult.errorMessage,
                    phoneError = phoneResult.errorMessage
                )
            }
            return
        }

        updateProfile()
    }

    private fun updateProfile(){
        viewModelScope.launch {
            updateUserUseCase.execute(_state.value.toUserDto())
        }
    }

    private fun nullifyErrorsIfAny(type: Int){
        when(type){
            NAME -> {
                validationUseCases.validateNameUseCase.execute(_state.value.name).also { validationResult ->
                    _state.update {
                        it.copy(
                            nameError = validationResult.errorMessage
                        )
                    }
                }
            }
            EMAIL -> {
                validationUseCases.validateEmailUseCase.execute(_state.value.email).also { validationResult ->
                    _state.update {
                        it.copy(
                            emailError = validationResult.errorMessage
                        )
                    }
                }
            }
            PHONE -> {
                validationUseCases.validatePhoneUseCase.execute(_state.value.phone).also { validationResult ->
                    _state.update {
                        it.copy(
                            phoneError = validationResult.errorMessage
                        )
                    }
                }
            }
        }
    }

    companion object {
        const val NAME = 1
        const val EMAIL = 2
        const val PHONE = 3
    }
}