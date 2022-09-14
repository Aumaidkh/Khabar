package com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment< Binding : ViewBinding>(
    layoutID: Int
) : Fragment(layoutID) {


    protected abstract val binding: Binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupListeners()
        setupObservers()
    }

    abstract fun setupViews()

    abstract fun setupListeners()

    abstract fun setupObservers()
}