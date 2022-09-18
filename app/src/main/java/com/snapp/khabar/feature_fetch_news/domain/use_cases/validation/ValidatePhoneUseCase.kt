package com.snapp.khabar.feature_fetch_news.domain.use_cases.validation

import com.snapp.khabar.feature_fetch_news.domain.util.ValidationResult

class ValidatePhoneUseCase {

    fun execute(phone: String): ValidationResult {
        if (phone.isEmpty()){
            return ValidationResult(
                successful = false,
                errorMessage = "Phone Can't be empty"
            )
        }

        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}