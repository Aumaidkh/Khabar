package com.snapp.khabar.feature_fetch_news.data.remote

import com.snapp.khabar.feature_fetch_news.data.remote.dto.NewsDto
import com.snapp.khabar.feature_fetch_news.data.util.Constants.EVERYTHING_NEWS
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun fetchTopHeadlines(
         @QueryMap options: Map<String,String>
    ): Response<NewsDto>

    @GET(EVERYTHING_NEWS)
    suspend fun fetchEverything(
        @QueryMap options: Map<String, String>
    ): Response<NewsDto>

}