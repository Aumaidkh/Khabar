package com.snapp.khabar.feature_fetch_news.presentation.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.snapp.khabar.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.splashScreenTheme)
        setContentView(R.layout.activity_login)
    }
}