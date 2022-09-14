package com.snapp.khabar.feature_fetch_news.domain.use_cases

import com.snapp.khabar.feature_fetch_news.domain.util.ValidationResult

class ValidateCommentUseCase {

    operator fun invoke(comment: String): ValidationResult {
        if (comment.isEmpty()){
            return ValidationResult(
                successful = false,
                errorMessage = "Comment can't be empty"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}