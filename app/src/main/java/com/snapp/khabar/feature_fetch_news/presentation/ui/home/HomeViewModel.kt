package com.snapp.khabar.feature_fetch_news.presentation.ui.home

import androidx.lifecycle.*
import com.snapp.khabar.feature_fetch_news.data.local.entities.NewsEntity
import com.snapp.khabar.feature_fetch_news.data.util.Constants.ALL_NEWS_CATEGORY
import com.snapp.khabar.feature_fetch_news.data.util.Constants.BUSINESS_CATEGORY
import com.snapp.khabar.feature_fetch_news.data.util.Constants.POLITICS_CATEGORY
import com.snapp.khabar.feature_fetch_news.data.util.Constants.SCIENCE_CATEGORY
import com.snapp.khabar.feature_fetch_news.data.util.Constants.SPORTS_CATEGORY
import com.snapp.khabar.feature_fetch_news.data.util.Constants.TECH_CATEGORY
import com.snapp.khabar.feature_fetch_news.domain.use_cases.*
import com.snapp.khabar.feature_fetch_news.domain.use_cases.news.local.DeleteNewsEntityUseCase
import com.snapp.khabar.feature_fetch_news.domain.use_cases.news.local.GetAllNewsFromLocalDatabaseUseCase
import com.snapp.khabar.feature_fetch_news.domain.use_cases.news.local.InsertNewsEntityUseCase
import com.snapp.khabar.feature_fetch_news.domain.use_cases.news.remote.FetchAllNewsUseCase
import com.snapp.khabar.feature_fetch_news.domain.use_cases.news.remote.FetchHeadlinesUseCase
import com.snapp.khabar.feature_fetch_news.domain.use_cases.news.remote.FetchNewsFromFirebaseFirestoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getAllNewsUseCase: GetAllNewsFromLocalDatabaseUseCase,
    fetchAllNewsFromFirebaseFirestore: FetchNewsFromFirebaseFirestoreUseCase,
    private val insertNewsEntityUseCase: InsertNewsEntityUseCase,
    private val deleteNewsEntityUseCase: DeleteNewsEntityUseCase,
    private val fetchAllNewsUseCase: FetchAllNewsUseCase,
    private val fetchHeadlinesUseCase: FetchHeadlinesUseCase
): ViewModel() {

    // Headlines
    val headlines  = fetchHeadlinesUseCase.invoke().asLiveData()
    // All News Observables
    val allNewsLiveData = fetchAllNewsFromFirebaseFirestore.invoke(ALL_NEWS_CATEGORY).asLiveData()


    // Business
    val businessNewsLiveData = fetchAllNewsFromFirebaseFirestore.invoke(BUSINESS_CATEGORY).asLiveData()

    // Politics
    val politicsNewsLiveData = fetchAllNewsFromFirebaseFirestore.invoke(POLITICS_CATEGORY).asLiveData()

    // Tech
    val techNewsLiveData = fetchAllNewsFromFirebaseFirestore.invoke(TECH_CATEGORY).asLiveData()

    // Science
    val scienceNewsLiveData = fetchAllNewsFromFirebaseFirestore.invoke(SCIENCE_CATEGORY).asLiveData()

    // Sports
    val sportsNewsLiveData = fetchAllNewsFromFirebaseFirestore.invoke(SPORTS_CATEGORY).asLiveData()

    /**
     * Saved Articles Observable
     * */
    val savedNews = getAllNewsUseCase.invoke().asLiveData()



    /**
     * Inserting news entity in database
     * */
    fun insertNewsEntity(newsEntity: NewsEntity){
        viewModelScope.launch {
            insertNewsEntityUseCase.invoke(newsEntity)
        }
    }

    /**
     * Deleting news entity in database
     * */
    fun deleteNewsEntity(newsEntity: NewsEntity){
        viewModelScope.launch {
            deleteNewsEntityUseCase.invoke(newsEntity)
        }
    }

}