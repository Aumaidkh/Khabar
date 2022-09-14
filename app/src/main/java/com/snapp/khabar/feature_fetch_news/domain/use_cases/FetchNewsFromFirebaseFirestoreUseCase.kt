package com.snapp.khabar.feature_fetch_news.domain.use_cases

import android.util.Log
import com.snapp.khabar.feature_fetch_news.domain.mappers.toArticleModel
import com.snapp.khabar.feature_fetch_news.domain.model.ArticleModel
import com.snapp.khabar.feature_fetch_news.domain.repository.RemoteNewsRepository
import com.snapp.khabar.feature_fetch_news.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val TAG = "FetchNewsFromFirebaseFi"

/**
 * Fetches all news from the fire store then filters and emits the news of
 * a specific category
 * */
class FetchNewsFromFirebaseFirestoreUseCase @Inject constructor(
    private val repo: RemoteNewsRepository
) {

    /**
     * The type of news to be fetched from the fire store
     * @param category
     * @return flow of articles
     * */
    operator fun invoke(category: String): Flow<Result<List<ArticleModel>>> {
        Log.d(TAG, "invoke: ")
        return flow {
            emit(Result.Loading())
            emit(
                Result.Success(repo.fetchNewsFrom("AllNews")
//                        .filter {
//                            it.category.equals(category, true)
//                        }
                    .map { it.toArticleModel() })
            )
        }
    }
}