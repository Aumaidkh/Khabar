package com.snapp.khabar.feature_fetch_news.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticleModel(
    val heading: String,
    val time: Long?,
    val desc: String,
    val image: String,
    val url: String,
    val id: String
): Parcelable
