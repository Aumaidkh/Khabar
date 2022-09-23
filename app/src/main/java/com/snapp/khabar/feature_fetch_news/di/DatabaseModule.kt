package com.snapp.khabar.feature_fetch_news.di

import android.app.Application
import androidx.room.Room
import com.snapp.khabar.feature_fetch_news.data.local.entities.NewsDataBase
import com.snapp.khabar.feature_fetch_news.data.repository.news.LocalRepositoryImpl
import com.snapp.khabar.feature_fetch_news.domain.repository.LocalRepository
import com.snapp.khabar.feature_fetch_news.domain.use_cases.DeleteNewsEntityUseCase
import com.snapp.khabar.feature_fetch_news.domain.use_cases.InsertNewsEntityUseCase
import com.snapp.khabar.feature_fetch_news.domain.use_cases.GetAllNewsFromLocalDatabaseUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent ::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(application: Application): NewsDataBase{
        return Room.databaseBuilder(application,NewsDataBase::class.java,"Newsdatabase").build()
    }

    @Provides
    @Singleton
    fun providesRepository(db:NewsDataBase): LocalRepository {
        return LocalRepositoryImpl(db)
    }

    @Provides
    @Singleton
    fun providesInsertNewsEntity(localRepository: LocalRepository) : InsertNewsEntityUseCase {
       return InsertNewsEntityUseCase(localRepository)

    }

    @Provides
    @Singleton
    fun providesDeleteNewsEntity(localRepository: LocalRepository) : DeleteNewsEntityUseCase{
        return DeleteNewsEntityUseCase(localRepository)
    }

    @Provides
    @Singleton
    fun providesAllNewsEntities(localRepository: LocalRepository) : GetAllNewsFromLocalDatabaseUseCase {
        return GetAllNewsFromLocalDatabaseUseCase(localRepository)
    }

}