package com.zenitech.imaapp.domain.usecases.sign_in

import com.zenitech.imaapp.data.auth.AuthenticationService

class SignOutUseCase(
    private val repository: AuthenticationService,
) {

    operator fun invoke() {
        return repository.signOut()
    }

}