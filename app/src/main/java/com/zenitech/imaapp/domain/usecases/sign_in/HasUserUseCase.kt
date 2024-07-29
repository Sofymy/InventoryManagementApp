package com.zenitech.imaapp.domain.usecases.sign_in

import com.zenitech.imaapp.data.auth.AuthenticationService

class HasUserUseCase(
    private val repository: AuthenticationService,
) {

    operator fun invoke(): Boolean {
        return repository.hasUser
    }

}