package com.snapp.khabar.feature_fetch_news.presentation.ui.home.fragments.search

sealed class SearchEvents {

    sealed class Actions{
        object ActionBack: Actions()
        data class OnSearchQueryChanged(
            val query: String
        ): Actions()
    }

    sealed class UiEvents {
        object CloseScreen: UiEvents()

    }
}
