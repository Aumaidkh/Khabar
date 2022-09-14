package com.snapp.khabar.feature_fetch_news.domain.util

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