package com.snapp.khabar.feature_fetch_news.domain.use_cases

import com.snapp.khabar.feature_fetch_news.data.local.entities.NewsEntity
import com.snapp.khabar.feature_fetch_news.domain.repository.LocalRepository
import javax.inject.Inject

class DeleteNewsEntityUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {

    suspend operator fun invoke(newsEntity: NewsEntity){
        localRepository.deleteNewsEntity(newsEntity)
    }


}