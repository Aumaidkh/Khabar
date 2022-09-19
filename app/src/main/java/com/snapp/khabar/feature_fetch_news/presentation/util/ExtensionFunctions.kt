package com.snapp.khabar.feature_fetch_news.presentation.util

import android.net.Uri
import com.snapp.khabar.feature_fetch_news.presentation.ui.edit_account.GenderEnum
import java.text.SimpleDateFormat
import java.util.*


/**
 * Formats the time from millis to a date month year format*/
fun Long.getFormattedTime(): String{
    val dateFormat = SimpleDateFormat("dd MMM yyyy")
    return Date(dateFormat.format(Date(this))).toString()
}

/**
 * Returns a gender enum
 * */
fun String.toGenderEnum(): GenderEnum {
    return if (this.equals("Male",true)){
        GenderEnum.Male
    }else{
        GenderEnum.Female
    }
}

/**
 * Returns if a uri is a local uri
 * */
fun Uri?.isLocalUri(): Boolean{
    return !this.toString().startsWith("http")
}