package com.snapp.khabar.feature_fetch_news.core

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewbinding.ViewBinding

open class DataBindingActivity<T: ViewBinding>(
    @LayoutRes private val layoutId: Int
): AppCompatActivity() {

    private var _binding: T? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this,layoutId)
        binding.initialize()
    }

    open fun T.initialize(){

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}