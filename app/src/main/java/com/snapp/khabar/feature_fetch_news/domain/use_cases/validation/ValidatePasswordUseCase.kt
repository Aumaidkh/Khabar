package com.snapp.khabar.feature_fetch_news.domain.use_cases.validation

import com.snapp.khabar.feature_fetch_news.domain.util.ValidationResult

class ValidatePasswordUseCase {

    operator fun invoke(
        password: String,
        rePassword: String
    ): ValidationResult {

        if (password.isEmpty()){
            return ValidationResult(
                successful = false,
                errorMessage = "Password can't be empty"
            )
        }

        if (rePassword.isEmpty()){
            return ValidationResult(
                successful = false,
                errorMessage = "Password can't be empty"
            )
        }

        if (password.length < 8){
            return ValidationResult(
                successful = false,
                errorMessage = "Password needs to be less than 8 chars"
            )
        }

        if (!password.equals(rePassword,false)){
            return ValidationResult(
                successful = false,
                errorMessage = "Passwords don't match"
            )
        }

        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}