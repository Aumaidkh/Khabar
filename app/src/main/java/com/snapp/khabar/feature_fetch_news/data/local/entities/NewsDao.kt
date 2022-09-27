package com.snapp.khabar.feature_fetch_news.data.local.entities

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsEntity(newsEntity: NewsEntity)

    @Delete
    suspend fun deleteNewsEntity(newsEntity: NewsEntity)

    @Query("SELECT * FROM EntityTable")
    fun getAllNewsEntities(): Flow<List<NewsEntity>>

}


