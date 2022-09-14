package com.snapp.khabar.feature_fetch_news.domain.util

sealed class Result<T>(
    val data: T? = null,
    val message: String? = null
){
    class Loading<T>: Result<T>()

    class Success<T>(private val articleList: T): Result<T>(articleList)

    class Error<T>(private val errorMessage: String): Result<T>(null,errorMessage)
}
