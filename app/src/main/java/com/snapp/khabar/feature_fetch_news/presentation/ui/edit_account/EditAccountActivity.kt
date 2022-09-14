package com.snapp.khabar.feature_fetch_news.presentation.ui.edit_account


import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.snapp.khabar.R


class EditAccountActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_account)

        val backButton = findViewById<View>(R.id.btnBack) as ImageButton
        backButton.setOnClickListener { finish() }
    }

}
