package com.snapp.khabar.feature_fetch_news.domain.util

import com.google.firebase.auth.FirebaseUser
import com.snapp.khabar.feature_fetch_news.data.remote.dto.UserDto
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


fun String.getFormattedTimeStamp(): String {
    // 1. Get Current Date
    // 2. Check if timestamp date == current date
    // 3. Return Today at HH:mm
    val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val outputFormatter = SimpleDateFormat("dd EEE MMM yyyy")
    var date: Date?
    return try {
        date = inputFormatter.parse(this)
        outputFormatter.format(date)
    }catch (e: Exception){
        ""
    }
}


/**
 * Mapping Firebase user to user dto
 * will be used in auth repository to map the user
 * when user is created successfully*/
fun FirebaseUser?.toUserDto(): UserDto? {
    if (this!=null){
        return UserDto(
            uid = uid,
            name = displayName ?: "No Name",
            email = email,
            photoUrl = photoUrl.toString(),
            phoneNumber = phoneNumber
        )
    }
    return null
}