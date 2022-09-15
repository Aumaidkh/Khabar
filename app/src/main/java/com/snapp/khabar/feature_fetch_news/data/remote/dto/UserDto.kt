package com.snapp.khabar.feature_fetch_news.data.remote.dto

data class UserDto(
    val uid: String,
    val name: String?,
    val email: String?,
    val photoUrl: String?,
    val phoneNumber: String?
)
