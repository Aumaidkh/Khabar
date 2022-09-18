package com.snapp.khabar.feature_fetch_news.domain.use_cases.validation

import com.snapp.khabar.feature_fetch_news.domain.util.ValidationResult

class ValidateNameUseCase {

    fun execute(name: String): ValidationResult {
        if (name.isEmpty()){
            return ValidationResult(
                successful = false,
                errorMessage = "Name can't be empty"
            )
        }

        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}