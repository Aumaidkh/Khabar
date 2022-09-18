package com.snapp.khabar.feature_fetch_news.domain.use_cases.validation

import android.util.Patterns
import com.snapp.khabar.feature_fetch_news.domain.util.ValidationResult

class ValidateEmailUseCase {

    fun execute(email: String): ValidationResult {
        if(email.isEmpty()){
            return ValidationResult(
                successful = false,
                errorMessage = "Email can't be empty"
            )
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return ValidationResult(
                successful = false,
                errorMessage = "Invalid Email"
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}