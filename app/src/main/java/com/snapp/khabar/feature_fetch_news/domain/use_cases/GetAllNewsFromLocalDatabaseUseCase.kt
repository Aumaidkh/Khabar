package com.snapp.khabar.feature_fetch_news.domain.use_cases

import com.snapp.khabar.feature_fetch_news.domain.mappers.toArticleModel
import com.snapp.khabar.feature_fetch_news.domain.model.ArticleModel
import com.snapp.khabar.feature_fetch_news.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllNewsFromLocalDatabaseUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {

    operator fun invoke(): Flow<List<ArticleModel>> {
        return localRepository.getNewsEntities().map {
            it.map { newsEntity -> newsEntity.toArticleModel()  }
        }
    }

}