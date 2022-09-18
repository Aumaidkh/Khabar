package com.snapp.khabar.feature_fetch_news.presentation.ui.edit_account

import android.net.Uri

sealed class EditProfileEvents {

    data class OnGenderChange(
        val gender: GenderEnum
    ): EditProfileEvents()

    data class OnNameChange(
        val name: String
    ): EditProfileEvents()

    data class OnEmailChange(
        val email: String
    ): EditProfileEvents()

    data class OnPhoneChanged(
        val phone: String
    ): EditProfileEvents()

    data class OnProfilePicChanged(
        val imageUri: Uri
    ): EditProfileEvents()

    object SaveChanges: EditProfileEvents()

    sealed class UiEvents {
        data class ShowSnackBar(
            val message: String
        ): UiEvents()

        data class ShowToast(
            val message: String
        ): UiEvents()
    }

}