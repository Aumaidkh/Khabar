package com.snapp.khabar.feature_fetch_news.presentation.util

import java.text.SimpleDateFormat
import java.util.*

object HelperFunctions {


    fun getFormattedTimeStamp(timestamp: String): String{
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
        val dateFormatter = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
        val date = inputFormat.parse(timestamp)
        return dateFormatter.format(date)
    }

    fun getTimestampFromMillis(millis: Long): String {
        val simpleDateFormat = SimpleDateFormat("HH:mm dd-mm-yy")
        val date = Date(millis)
        return simpleDateFormat.format(date)
    }
}