package com.snapp.khabar.feature_fetch_news.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val uid: String? = null,
    val name: String? = null,
    val email: String? = null,
    val photoUrl: String? = null,
    val phoneNumber: String? = null
)
