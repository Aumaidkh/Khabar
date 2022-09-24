package com.snapp.khabar.feature_fetch_news.di

import com.snapp.khabar.feature_fetch_news.domain.repository.auth.AuthRepository
import com.snapp.khabar.feature_fetch_news.domain.repository.auth.EmailAndPasswordAuth
import com.snapp.khabar.feature_fetch_news.domain.use_cases.auth.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun providesAuthUseCases(
        authenticateUserWithGoogle: AuthenticateUserWithGoogleUseCase,
        checkIfUserIsAuthenticated: CheckIfUserIsAuthenticatedUseCase,
        createUserWithEmailAndPassword: CreateUserWithEmailAndPasswordUseCase,
        signInWithEmailAndPassword: SignInWithEmailAndPasswordUseCase,
        signOut: SignOutUseCase
    ): AuthUseCases {
        return AuthUseCases(
            authenticateUserWithGoogle,
            checkIfUserIsAuthenticated,
            createUserWithEmailAndPassword,
            signInWithEmailAndPassword,
            signOut
        )
    }

    /**
     * Sign in User With Email Password
     * */
    @Provides
    @Singleton
    fun provideSignInUserWithEmailPasswordUseCase(
        repository: EmailAndPasswordAuth
    ): SignInWithEmailAndPasswordUseCase {
        return SignInWithEmailAndPasswordUseCase(repository)
    }


    /**
     * Create User With Email Password
     * */
    @Provides
    @Singleton
    fun provideCreateUserWithEmailPasswordUseCase(
        repository: EmailAndPasswordAuth
    ): CreateUserWithEmailAndPasswordUseCase {
        return CreateUserWithEmailAndPasswordUseCase(repository)
    }

    /**
     * Provides Sign out use case
     * */
    @Provides
    @Singleton
    fun provideSignOutUseCase(repository: AuthRepository): SignOutUseCase {
        return SignOutUseCase(repository)
    }

    /**
     * Checking if user is already logged in
     * */
    @Provides
    @Singleton
    fun provideCheckIfUserIsAuthenticatedUseCase(repository: AuthRepository): CheckIfUserIsAuthenticatedUseCase {
        return CheckIfUserIsAuthenticatedUseCase(repository)
    }

    /**
     * Authenticate user with google sign in
     * */
    @Provides
    @Singleton
    fun provideAuthenticateUserUseCase(repository: AuthRepository): AuthenticateUserWithGoogleUseCase {
        return AuthenticateUserWithGoogleUseCase(repository)
    }

}