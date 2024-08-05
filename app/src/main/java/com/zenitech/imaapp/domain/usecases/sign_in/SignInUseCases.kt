package com.zenitech.imaapp.domain.usecases.sign_in

import com.zenitech.imaapp.data.auth.AuthenticationService

class SignInUseCases(
    val repository: AuthenticationService,
    val signInWithGoogle: SignInWithGoogleUseCase,
    val hasUser: HasUserUseCase,
    val signOut: SignOutUseCase
)