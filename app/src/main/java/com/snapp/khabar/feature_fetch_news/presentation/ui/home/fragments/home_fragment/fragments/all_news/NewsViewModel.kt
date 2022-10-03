package com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.home_fragment.fragments.all_news

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.snapp.khabar.feature_fetch_news.data.util.Constants.BUSINESS
import com.snapp.khabar.feature_fetch_news.data.util.Constants.BUSINESS_CATEGORY
import com.snapp.khabar.feature_fetch_news.data.util.Constants.CATEGORY
import com.snapp.khabar.feature_fetch_news.data.util.Constants.JOBS_CATEGORY
import com.snapp.khabar.feature_fetch_news.data.util.Constants.POLITICS_CATEGORY
import com.snapp.khabar.feature_fetch_news.data.util.Constants.SCIENCE_CATEGORY
import com.snapp.khabar.feature_fetch_news.data.util.Constants.SPORTS_CATEGORY
import com.snapp.khabar.feature_fetch_news.data.util.Constants.TECH
import com.snapp.khabar.feature_fetch_news.data.util.Constants.TECH_CATEGORY
import com.snapp.khabar.feature_fetch_news.domain.use_cases.news.remote.FetchNewsPagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

private const val TAG = "NewsViewModel"
@HiltViewModel
class NewsViewModel @Inject constructor(
    private val fetchNewsPagesUseCase: FetchNewsPagesUseCase
): ViewModel() {

    /**
     * All News Flow
     * */
    val news = fetchNewsPagesUseCase.invoke(CATEGORY,"",false)
        .cachedIn(
        viewModelScope
    )

    val business = fetchNewsPagesUseCase.invoke(CATEGORY, BUSINESS_CATEGORY,true).cachedIn(
        viewModelScope
    )

    val tech = fetchNewsPagesUseCase.invoke(CATEGORY, TECH_CATEGORY,true).cachedIn(
        viewModelScope
    )

    val politics = fetchNewsPagesUseCase.invoke(CATEGORY, POLITICS_CATEGORY,true).cachedIn(
        viewModelScope
    )

    val science = fetchNewsPagesUseCase.invoke(CATEGORY, SCIENCE_CATEGORY,true).cachedIn(
        viewModelScope
    )

    val sports = fetchNewsPagesUseCase.invoke(CATEGORY, SPORTS_CATEGORY,true).cachedIn(
        viewModelScope
    )

    val jobs = fetchNewsPagesUseCase.invoke(CATEGORY, JOBS_CATEGORY,true).cachedIn(
        viewModelScope
    )
}