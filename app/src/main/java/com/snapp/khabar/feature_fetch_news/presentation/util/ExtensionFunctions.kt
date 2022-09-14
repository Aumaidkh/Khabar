package com.snapp.khabar.feature_fetch_news.presentation.util

import java.text.SimpleDateFormat
import java.util.*


/**
 * Formats the time from millis to a date month year format*/
fun Long.getFormattedTime(): String{
    val dateFormat = SimpleDateFormat("dd MMM yyyy")
    return Date(dateFormat.format(Date(this))).toString()
}