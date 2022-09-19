package com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.snapp.khabar.feature_fetch_news.data.local.DatastoreManager
import com.snapp.khabar.feature_fetch_news.data.repository.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val _settingsEventFlow = MutableSharedFlow<SettingsUiEvent>()
    val eventFlow = _settingsEventFlow.asSharedFlow()


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
                Log.d(TAG, "onEvent: Navigate Edit Profile")
                navigateToEditProfileScreen()
            }

            is SettingsScreenEvent.InitScreenState -> {
                setupScreenScreen()
            }
        }
    }

    private fun navigateToEditProfileScreen() {
        viewModelScope.launch {
            _settingsEventFlow.emit(SettingsUiEvent.NavigateToEditProfileScreen(
                _state.value.toUserDto()
            ))
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            signOutUseCase.invoke()
            _settingsEventFlow.emit(SettingsUiEvent.NavigateUserToLoginScreen)
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