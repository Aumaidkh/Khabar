package com.snapp.khabar.feature_fetch_news.di

import com.snapp.khabar.feature_fetch_news.domain.use_cases.validation.*
import com.snapp.khabar.feature_fetch_news.domain.use_cases.validation.ValidationUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ValidationUseCases {

    /**
     * Full Validation Use Case
     * */
    @Provides
    @Singleton
    fun providesValidationUseCases(
        validateName: ValidateNameUseCase,
        validateEmail: ValidateEmailUseCase,
        validatePhone: ValidatePhoneUseCase,
        validateComment: ValidateCommentUseCase,
        validatePasswordUseCase: ValidatePasswordUseCase
    ): ValidationUseCases{
        return ValidationUseCases(
            validateNameUseCase = validateName,
            validateCommentUseCase = validateComment,
            validateEmailUseCase = validateEmail,
            validatePhoneUseCase = validatePhone,
            validatePasswordUseCase = validatePasswordUseCase
        )
    }

    /**
     * Validate Password Use Case
     * */
    @Provides
    @Singleton
    fun providesValidatePasswordUseCase() =
        ValidatePasswordUseCase()

    /**
     * Validate Full Name*/
    @Provides
    @Singleton
    fun providesValidateFullNameUseCase() =
        ValidateNameUseCase()

    /**
     * Validate Email*/
    @Provides
    @Singleton
    fun providesValidateEmailUseCase() =
        ValidateEmailUseCase()

    /**
     * Validate Phone*/
    @Provides
    @Singleton
    fun providesValidatePhoneUseCase() =
        ValidatePhoneUseCase()

    /**
     * Validate Comment*/
    @Provides
    @Singleton
    fun providesValidateCommentUseCase() =
        ValidateCommentUseCase()
}