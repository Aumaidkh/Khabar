package com.snapp.khabar.feature_fetch_news.presentation.ui.edit_account

import android.net.Uri
import androidx.core.net.toUri

data class ProfileState(
    val isLoading: Boolean? = null,
    val name: String = "",
    val nameError: String? = null,
    val gender: GenderEnum = GenderEnum.Male,
    val email: String = "",
    val emailError: String? = null,
    val phone: String = "",
    val phoneError: String? = null,
    val imageUri: Uri = "".toUri()
)
