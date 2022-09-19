package com.snapp.khabar.feature_fetch_news.presentation.ui.edit_account

import android.net.Uri
import androidx.core.net.toUri
import com.snapp.khabar.feature_fetch_news.data.remote.dto.UserDto

data class ProfileState(
    val isLoading: Boolean? = null,
    val userId: String = "",
    val name: String = "",
    val nameError: String? = null,
    val gender: GenderEnum? = null,
    val email: String = "",
    val emailError: String? = null,
    val phone: String = "",
    val phoneError: String? = null,
    val imageUri: Uri? = null
)

fun ProfileState.toUserDto() =
    UserDto(
        name = name,
        email = email,
        photoUrl = imageUri.toString(),
        phoneNumber = phone,
        gender = gender?.name
    )
