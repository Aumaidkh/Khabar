package com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snapp.khabar.feature_fetch_news.domain.mappers.toArticleModel
import com.snapp.khabar.feature_fetch_news.domain.use_cases.news.remote.RemoteNewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val newsUseCases: RemoteNewsUseCases
) : ViewModel() {

    /**
     * State
     * */
    private val _state = MutableStateFlow(SearchScreenState())
    val state = _state.asStateFlow()

    /**
     * Query Flow
     * */
    private val _queryFlow = MutableStateFlow(SearchQuery())
    private val _results = MutableStateFlow(SearchResults())


    /**
     * Results Flow
     * */
    val results = _queryFlow.combine(_state) { query, state ->
        val items = newsUseCases.searchNewsByUseCase.invoke(
            query = query.searchQuery
        )
        SearchScreenState(
            searchQuery = query.searchQuery,
            searchResults = items.map { it.toArticleModel() }
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        SearchScreenState()
    )

    /**
     * Events Channel
     **/
    private val _events = Channel<SearchEvents.UiEvents>()
    val eventFlow = _events.receiveAsFlow()

    fun onEvent(event: SearchEvents.Actions) {
        when (event) {
            is SearchEvents.Actions.ActionBack -> {
                viewModelScope.launch {
                    _events.send(SearchEvents.UiEvents.CloseScreen)
                }
            }

            is SearchEvents.Actions.OnSearchQueryChanged -> {
                _queryFlow.update {
                    it.copy(
                        searchQuery = event.query
                    )
                }
            }
        }
    }
}