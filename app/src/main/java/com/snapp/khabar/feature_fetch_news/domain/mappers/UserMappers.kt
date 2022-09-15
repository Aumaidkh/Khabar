package com.snapp.khabar.feature_fetch_news.domain.mappers

import com.snapp.khabar.feature_fetch_news.data.remote.dto.UserDto
import com.snapp.khabar.feature_fetch_news.domain.model.UserModel

fun UserDto.toUserModel() =
    UserModel(
        userId = uid,
        email = email,
        name = name,
        photoUrl = photoUrl,
        phoneNumber = phoneNumber
    )