package com.snapp.khabar.feature_fetch_news.presentation.ui.news_detail

import com.snapp.khabar.feature_fetch_news.domain.model.ArticleModel

sealed class NewsDetailsEvents {

    sealed class UserEvents {
        object BackButtonClick: UserEvents()
        data class ShareButtonClick(
            val link: String
        ): UserEvents()
        data class BookmarkButtonClick(
            val articleModel: ArticleModel
        ): UserEvents()
    }

    sealed class UiEvents {
        data class ShowSnackBar(
            val message: String
        ): UiEvents()

        object CloseScreen: UiEvents()

        data class NavigateToShareScreen(
            val link: String
        ): UiEvents()

    }
}
