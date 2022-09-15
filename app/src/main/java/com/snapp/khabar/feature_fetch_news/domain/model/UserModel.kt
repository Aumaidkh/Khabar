package com.snapp.khabar.feature_fetch_news.domain.model

data class UserModel(
    val userId: String,
    val email: String?,
    val name: String?,
    val photoUrl: String?,
    val phoneNumber: String?
)
