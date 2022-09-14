package com.snapp.khabar.feature_fetch_news.domain.use_cases

import android.util.Log
import com.snapp.khabar.feature_fetch_news.domain.mappers.toArticleModel
import com.snapp.khabar.feature_fetch_news.domain.model.ArticleModel
import com.snapp.khabar.feature_fetch_news.domain.repository.RemoteNewsRepository
import com.snapp.khabar.feature_fetch_news.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


/**
 * Gets hold of all news collection and filters the data set
 * by value of is Headline
 * */
private const val TAG = "FetchHeadlinesUseCase"
class FetchHeadlinesUseCase @Inject constructor(
    private val repo: RemoteNewsRepository
) {

    operator fun invoke(): Flow<Result<List<ArticleModel>>> {
        return flow {
            emit(Result.Loading())
            try {
                val data = repo.fetchNewsFrom("AllNews").filter {
                    it.isHeadline
                }.map { it.toArticleModel() }
                Log.d(TAG, "invoke: $data")
                emit(Result.Success(repo.fetchNewsFrom("AllNews").filter {
                    it.isHeadline
                }.map { it.toArticleModel() }))
            } catch (e: Exception){
                emit(Result.Error(e.message.toString()))
            }
        }
    }
}