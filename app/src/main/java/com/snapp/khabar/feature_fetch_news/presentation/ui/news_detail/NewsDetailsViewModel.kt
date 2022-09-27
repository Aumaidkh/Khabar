package com.snapp.khabar.feature_fetch_news.presentation.ui.news_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snapp.khabar.feature_fetch_news.domain.mappers.toNewsEntity
import com.snapp.khabar.feature_fetch_news.domain.use_cases.news.local.InsertNewsEntityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailsViewModel @Inject constructor(
    private val insertNewsEntityUseCase: InsertNewsEntityUseCase
): ViewModel() {

    /**
     * Events Channel
     * */
    private val _events = Channel<NewsDetailsEvents.UiEvents>()
    val eventFlow = _events.receiveAsFlow()


    fun onEvent(event: NewsDetailsEvents.UserEvents){
        when(event){

            is NewsDetailsEvents.UserEvents.BackButtonClick -> {
                /**
                 * Closes the current screen
                 * */
                viewModelScope.launch {
                    _events.send(NewsDetailsEvents.UiEvents.CloseScreen)
                }
            }

            is NewsDetailsEvents.UserEvents.ShareButtonClick -> {
                /**
                 * Opens up the share intent with the link
                 * */
                viewModelScope.launch {
                    _events.send(NewsDetailsEvents.UiEvents.NavigateToShareScreen(event.link))
                }
            }

            is NewsDetailsEvents.UserEvents.BookmarkButtonClick -> {
                /**
                 * Add The Item to Bookmarks
                 * */
                 viewModelScope.launch {
                     insertNewsEntityUseCase.invoke(event.articleModel.toNewsEntity())
                     _events.send(NewsDetailsEvents.UiEvents.ShowSnackBar("Item Saved To Bookmarks"))
                 }
            }

        }
    }
}