package com.snapp.khabar.feature_fetch_news.presentation.util

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.net.toUri
import com.google.android.material.snackbar.Snackbar
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


/**
 * Make status bar transparent
 * */
fun Activity.showTransparentStatusBar(){
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    window.statusBarColor = Color.TRANSPARENT
}

/**
 * Open Url in browser intent
 * */
fun Activity.openIntentInBrowser(url: String){
    Intent(Intent.ACTION_VIEW).apply {
        data = url.toUri()
    }.also {
        startActivity(it)
    }
}

/**
 * Open Up Share Intent
 * */
fun Activity.showShareIntent(link: String){
    Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT,"Subject Here")
        putExtra(Intent.EXTRA_TEXT,link)
    }.also {
        startActivity(it)
    }
}


/**
 * Shows Snack Bar
 * */
fun View.showSnackBar(message: String){
    Snackbar.make(this,message,Snackbar.LENGTH_SHORT).show()
}