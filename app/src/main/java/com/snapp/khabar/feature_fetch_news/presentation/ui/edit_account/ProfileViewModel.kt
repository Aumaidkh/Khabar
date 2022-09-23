package com.snapp.khabar.feature_fetch_news.presentation.ui.edit_account

import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snapp.khabar.feature_fetch_news.data.util.UserResult
import com.snapp.khabar.feature_fetch_news.domain.use_cases.user.FetchUserFromDataStoreUseCase
import com.snapp.khabar.feature_fetch_news.domain.use_cases.user.UpdateUserUseCase
import com.snapp.khabar.feature_fetch_news.domain.use_cases.user.UploadProfilePhotoUseCase
import com.snapp.khabar.feature_fetch_news.domain.use_cases.validation.ValidationUseCases
import com.snapp.khabar.feature_fetch_news.presentation.util.isLocalUri
import com.snapp.khabar.feature_fetch_news.presentation.util.toGenderEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "ProfileViewModel"

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val validationUseCases: ValidationUseCases,
    private val updateUserUseCase: UpdateUserUseCase,
    private val fetchUserFromDataStoreUseCase: FetchUserFromDataStoreUseCase,
    private val uploadProfilePhotoUseCase: UploadProfilePhotoUseCase
) : ViewModel() {

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


    init {
        initStateFromDataStore()
    }

    /**
     * Initializes the state from the datastore
     * */
    private fun initStateFromDataStore() {
//        datastoreManager.getProfileDetails().onEach { userDto ->
//            _state.update {
//                it.copy(
//                    userId = userDto.uid?:"",
//                    name = userDto.name?:"",
//                    email = userDto.email?:"",
//                    phone = userDto.phoneNumber?:"",
//                    gender = userDto.gender?.toGenderEnum(),
//                    imageUri = userDto.photoUrl?.toUri() ?: "".toUri()
//                )
//            }
//        }.launchIn(viewModelScope)
        viewModelScope.launch {
            fetchUserFromDataStoreUseCase.invoke().also { userDto ->
                _state.update {
                    it.copy(
                        userId = userDto.uid ?: "",
                        name = userDto.name ?: "",
                        email = userDto.email ?: "",
                        phone = userDto.phoneNumber ?: "",
                        gender = userDto.gender?.toGenderEnum(),
                        imageUri = userDto.photoUrl?.toUri() ?: "".toUri()
                    )
                }
            }
        }
    }

    fun onEvent(event: EditProfileEvents) {
        when (event) {
            is EditProfileEvents.OnNameChange -> {
                _state.update {
                    it.copy(
                        name = event.name
                    )
                }
                updateErrorsIfAny(NAME)
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
                updateErrorsIfAny(EMAIL)
            }
            is EditProfileEvents.OnPhoneChanged -> {
                _state.update {
                    it.copy(
                        phone = event.phone
                    )
                }
                updateErrorsIfAny(PHONE)
            }

            is EditProfileEvents.OnProfilePicChanged -> {
                _state.update {
                    it.copy(
                        imageUri = event.imageUri
                    )
                }
            }

            is EditProfileEvents.PrePopulateInputFields -> {
                populateStateFromDataStore()
            }

            is EditProfileEvents.SaveChanges -> {
                saveChanges()
            }

        }
    }

    /**
     * Pre-populates the state from datastore and sending those values to the
     * screen ensuring the input fields of the screen are prefilled
     * */
    private fun populateStateFromDataStore() {
//        datastoreManager.getProfileDetails().onEach { userDto ->
//            _eventFlow.emit(
//                EditProfileEvents.UiEvents.PrepopulateInputs(
//                    name = userDto.name ?: "",
//                    email = userDto.email ?: "",
//                    phone = userDto.phoneNumber ?: "",
//                    imageUri = userDto.photoUrl?.toUri() ?: "".toUri(),
//                    gender = userDto.gender?.toGenderEnum() ?: GenderEnum.Male
//                )
//            )
//        }.launchIn(viewModelScope)
        viewModelScope.launch {
            fetchUserFromDataStoreUseCase.invoke().also { userDto ->
                _eventFlow.emit(
                    EditProfileEvents.UiEvents.PrepopulateInputs(
                        name = userDto.name ?: "",
                        email = userDto.email ?: "",
                        phone = userDto.phoneNumber ?: "",
                        imageUri = userDto.photoUrl?.toUri() ?: "".toUri(),
                        gender = userDto.gender?.toGenderEnum() ?: GenderEnum.Male
                    )
                )
            }
        }
    }

    /**
     * Saves changes to the user profile if there are no error inside the
     * input fields
     * */
    private fun saveChanges() {
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

        if (anyFieldHasError) {
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

    /**
     * Updates the profile of the user */
    private fun updateProfile() {
        viewModelScope.launch {
            uploadPhotoAndUpdateState()
            updateUserUseCase.execute(_state.value.toUserDto()).onEach { userResult ->
                when (userResult) {
                    is UserResult.Progress -> {
                        _state.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                    is UserResult.Complete.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                        _eventFlow.emit(
                            EditProfileEvents.UiEvents.ProfileUpdated
                        )
                    }

                    is UserResult.Complete.Failed -> {
                        _state.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                        _eventFlow.emit(
                            EditProfileEvents.UiEvents.ShowSnackBar(userResult.error)
                        )
                    }

                }
            }.launchIn(this)
        }
    }

    /**
     * Uploads the photo to storage and updates the state imageUri to the
     * url obtained after uploading the image*/
    private suspend fun uploadPhotoAndUpdateState() {
        if (_state.value.imageUri != null && _state.value.imageUri.isLocalUri()) {
            uploadProfilePhotoUseCase.invoke(_state.value.userId, _state.value.imageUri!!)
                .also { imageUrl ->
                    if (imageUrl != null) {
                        _state.update {
                            it.copy(
                                imageUri = imageUrl.toUri()
                            )
                        }
                    }
                }
        }
    }

    /**
     * Updates all the errors of the input fields
     * */
    private fun updateErrorsIfAny(type: Int) {
        when (type) {
            NAME -> {
                validationUseCases.validateNameUseCase.execute(_state.value.name)
                    .also { validationResult ->
                        _state.update {
                            it.copy(
                                nameError = validationResult.errorMessage
                            )
                        }
                    }
            }
            EMAIL -> {
                validationUseCases.validateEmailUseCase.execute(_state.value.email)
                    .also { validationResult ->
                        _state.update {
                            it.copy(
                                emailError = validationResult.errorMessage
                            )
                        }
                    }
            }
            PHONE -> {
                validationUseCases.validatePhoneUseCase.execute(_state.value.phone)
                    .also { validationResult ->
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