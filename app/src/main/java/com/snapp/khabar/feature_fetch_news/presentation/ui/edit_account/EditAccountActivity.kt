package com.snapp.khabar.feature_fetch_news.presentation.ui.edit_account


import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.snapp.khabar.R
import com.snapp.khabar.databinding.ActivityEditAccountBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditAccountActivity: AppCompatActivity() {

    /**
     * DataBinding Vars
     * */
    private var _binding: ActivityEditAccountBinding? = null
    private val binding get() = _binding!!


    /**
     * ViewModels
     * */


    /**
     * Other Vars
     * */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_account)

        setupClicks()
    }

    private fun setupClicks() {
        binding.apply {
            btnBack.setOnClickListener{
                finish()
            }

            btnSaveChanges.setOnClickListener {

            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
