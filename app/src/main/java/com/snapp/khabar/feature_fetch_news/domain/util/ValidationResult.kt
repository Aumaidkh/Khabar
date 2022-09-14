package com.snapp.khabar.feature_fetch_news.domain.util

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
