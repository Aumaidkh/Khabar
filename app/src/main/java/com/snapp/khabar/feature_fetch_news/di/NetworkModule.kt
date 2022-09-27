package com.snapp.khabar.feature_fetch_news.di

import com.snapp.khabar.feature_fetch_news.core.Constants.BASE_URL
import com.snapp.khabar.feature_fetch_news.data.remote.NewsApi
import com.snapp.khabar.feature_fetch_news.data.repository.news.NewsRepositoryImpl
import com.snapp.khabar.feature_fetch_news.domain.repository.news.NewsRepository
import com.snapp.khabar.feature_fetch_news.domain.repository.news.RemoteNewsRepository
import com.snapp.khabar.feature_fetch_news.domain.use_cases.news.remote.FetchAllNewsUseCase
import com.snapp.khabar.feature_fetch_news.domain.use_cases.news.remote.FetchHeadlinesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {



    // Provide Retrofit Instance
    @Provides
    @Singleton
    fun provideRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    // Provide Api Instance
    @Provides
    @Singleton
    fun provideNewsApiInstance(retrofit: Retrofit): NewsApi {
        return retrofit.create(NewsApi::class.java)
    }




    // Provide News Repository
    @Provides
    @Singleton
    fun provideNewsRepository(api: NewsApi) : NewsRepository {
        return NewsRepositoryImpl(api)
    }


}