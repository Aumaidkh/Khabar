package com.snapp.khabar.feature_fetch_news.domain.use_cases.validation

import javax.inject.Inject

data class ValidationUseCases @Inject constructor(
    val validateNameUseCase: ValidateNameUseCase,
    val validatePhoneUseCase: ValidatePhoneUseCase,
    val validateEmailUseCase: ValidateEmailUseCase,
    val validateCommentUseCase: ValidateCommentUseCase,
    val validatePasswordUseCase: ValidatePasswordUseCase
)
