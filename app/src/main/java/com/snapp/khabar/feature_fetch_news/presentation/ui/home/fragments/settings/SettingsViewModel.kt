package com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.settings

import androidx.lifecycle.ViewModel
import com.snapp.khabar.feature_fetch_news.data.local.DatastoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val datastore: DatastoreManager
): ViewModel() {


}