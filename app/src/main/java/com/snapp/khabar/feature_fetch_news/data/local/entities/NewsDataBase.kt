package com.snapp.khabar.feature_fetch_news.data.local.entities

import androidx.room.Database
import androidx.room.RoomDatabase
import com.snapp.khabar.feature_fetch_news.di.DatabaseModule
import kotlin.reflect.KClass

@Database(
    entities = [NewsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDataBase : RoomDatabase() {
    abstract fun getNewsDao(): NewsDao
}