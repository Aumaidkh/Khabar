package com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snapp.khabar.feature_fetch_news.data.local.DatastoreManager
import com.snapp.khabar.feature_fetch_news.data.repository.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val datastore: DatastoreManager,
    private val signOutUseCase: SignOutUseCase
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
                _state.update {
                    it.copy(darkModeEnabled = event.enabled)
                }
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
        }
    }

    private fun handlePhoneNumberClick(){
        viewModelScope.launch {
            _eventChannel.send(
                SettingsUiEvent.PhoneEvent()
            )
        }
    }

    private fun handleEmailClick(){
        viewModelScope.launch {
            _eventChannel.send(
                SettingsUiEvent.EmailEvent()
            )
        }
    }

    private fun handlePrivacyPolicyClick(){
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
        viewModelScope.launch {
            datastore.getProfileDetails().onEach { userDto ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        username = userDto.name ?: "No Name",
                        email = userDto.email ?: "abc@gmail.com",
                        imageUrl = userDto.photoUrl ?: "www.google.com",
                        userId = userDto.uid ?: "aafafa"
                    )
                }

            }.launchIn(this)
        }
    }
}

private const val TAG = "SettingsViewModel"