package com.snapp.khabar.feature_fetch_news.presentation.util

import android.app.Activity
import android.net.Uri
import androidx.appcompat.app.AppCompatDelegate
import com.snapp.khabar.feature_fetch_news.presentation.ui.edit_account.GenderEnum
import java.text.SimpleDateFormat
import java.util.*


/**
 * Formats the time from millis to a date month year format*/
fun Long.getFormattedTime(): String{
    SimpleDateFormat("dd MMM yyyy").also {
        return it.format(Date(this))
    }
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

/**
 * Enables Night Mode In activity*/
fun Activity.enableNightMode(isEnabled: Boolean) {
    if (isEnabled) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    } else {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

    }
}