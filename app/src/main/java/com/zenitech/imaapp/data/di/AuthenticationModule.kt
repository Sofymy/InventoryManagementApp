package com.zenitech.imaapp.data.di

import com.google.firebase.auth.FirebaseAuth
import com.zenitech.imaapp.data.auth.AuthenticationService
import com.zenitech.imaapp.data.auth.AuthenticationServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesAuthenticationService(
        firebaseAuth: FirebaseAuth
    ): AuthenticationService {
        return AuthenticationServiceImpl(firebaseAuth)
    }

}