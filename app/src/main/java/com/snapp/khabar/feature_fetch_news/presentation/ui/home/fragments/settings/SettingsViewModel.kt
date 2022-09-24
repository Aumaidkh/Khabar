package com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snapp.khabar.feature_fetch_news.domain.use_cases.auth.SignOutUseCase
import com.snapp.khabar.feature_fetch_news.domain.use_cases.settings.DarkModeToggleUseCase
import com.snapp.khabar.feature_fetch_news.domain.use_cases.settings.CheckIfDarkModeIsEnabledUseCase
import com.snapp.khabar.feature_fetch_news.domain.use_cases.settings.CheckIfNotificationIsEnabledUseCase
import com.snapp.khabar.feature_fetch_news.domain.use_cases.settings.NotificationsToggleUseCase
import com.snapp.khabar.feature_fetch_news.domain.use_cases.user.FetchUserFromDataStoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    // private val datastore: DatastoreManager,
    private val fetchUserFromDataStoreUseCase: FetchUserFromDataStoreUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val darkModeToggleUseCase: DarkModeToggleUseCase,
    private val notificationsToggleUseCase: NotificationsToggleUseCase,
    private val checkIfDarkModeIsEnabledUseCase: CheckIfDarkModeIsEnabledUseCase,
    private val checkIfNotificationIsEnabledUseCase: CheckIfNotificationIsEnabledUseCase
) : ViewModel() {

    /**
     * Settings screen state
     * */
    private val _state = MutableStateFlow(SettingState())
    val state = _state.asStateFlow()

    /**
     * Events Flow
     * */
    private val _eventChannel = Channel<SettingsUiEvent>()
    val eventFlow = _eventChannel.receiveAsFlow()


    fun onEvent(event: SettingsScreenEvent) {
        when (event) {
            is SettingsScreenEvent.DarkModeToggle -> {

            }

            is SettingsScreenEvent.NotificationToggle -> {
                _state.update {
                    it.copy(notificationEnabled = event.enabled)
                }
            }

            is SettingsScreenEvent.SignOut -> {
                signOut()
            }

            is SettingsScreenEvent.EditProfile -> {
                navigateToEditProfileScreen()
            }

            is SettingsScreenEvent.InitScreenState -> {
                setupScreenScreen()
            }

            is SettingsScreenEvent.PrivacyPolicyClickEvent -> {
                handlePrivacyPolicyClick()
            }

            is SettingsScreenEvent.EmailClickEvent -> {
                handleEmailClick()
            }

            is SettingsScreenEvent.PhoneNumberClickEvent -> {
                handlePhoneNumberClick()
            }

            is SettingsScreenEvent.IsDarkModeEnabled -> {
                emitDarkModeState()
            }


            is SettingsScreenEvent.IsNotificationsEnabled -> {
                emitNotificationState()
            }

            is SettingsScreenEvent.ApplyDarkMode -> {
                toggleDarkMode(event.isEnabled)
            }

            is SettingsScreenEvent.EnableNotification -> {
                toggleNotifications(event.isEnabled)
            }

        }
    }


    private fun toggleDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            darkModeToggleUseCase.invoke(enabled)
            emitDarkModeState()
        }
    }

    private fun toggleNotifications(enabled: Boolean) {
        viewModelScope.launch {
            notificationsToggleUseCase.invoke(enabled)
            emitNotificationState()
        }
    }

    private fun emitDarkModeState() {
        viewModelScope.launch {
            checkIfDarkModeIsEnabledUseCase.invoke().also {
                _eventChannel.send(
                    SettingsUiEvent.DarkModeToggle(
                        isEnabled = it
                    )
                )
            }
        }
    }

    private fun emitNotificationState() {
        viewModelScope.launch {
            checkIfNotificationIsEnabledUseCase.invoke().also {
                _eventChannel.send(
                    SettingsUiEvent.NotificationToggle(
                        isEnabled = it
                    )
                )
            }
        }
    }

    private fun handlePhoneNumberClick() {
        viewModelScope.launch {
            _eventChannel.send(
                SettingsUiEvent.PhoneEvent()
            )
        }
    }

    private fun handleEmailClick() {
        viewModelScope.launch {
            _eventChannel.send(
                SettingsUiEvent.EmailEvent()
            )
        }
    }

    private fun handlePrivacyPolicyClick() {
        viewModelScope.launch {
            _eventChannel.send(SettingsUiEvent.PrivacyPolicyEvent())
        }
    }

    private fun navigateToEditProfileScreen() {
        viewModelScope.launch {
            _eventChannel.send(SettingsUiEvent.NavigateToEditProfileScreen)
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            signOutUseCase.invoke()
            _eventChannel.send(SettingsUiEvent.NavigateUserToLoginScreen)
        }
    }

    private fun setupScreenScreen() {
//        viewModelScope.launch {
//            datastore.getProfileDetails().onEach { userDto ->
//                _state.update {
//                    it.copy(
//                        isLoading = false,
//                        username = userDto.name ?: "No Name",
//                        email = userDto.email ?: "abc@gmail.com",
//                        imageUrl = userDto.photoUrl ?: "www.google.com",
//                        userId = userDto.uid ?: "aafafa"
//                    )
//                }
//
//            }.launchIn(this)
        viewModelScope.launch {
            fetchUserFromDataStoreUseCase.invoke().also { userDto ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        username = userDto.name ?: "",
                        email = userDto.email ?: "",
                        imageUrl = userDto.photoUrl ?: "",
                        userId = userDto.uid ?: ""
                    )
                }
            }
        }
    }
}

private const val TAG = "SettingsViewModel"