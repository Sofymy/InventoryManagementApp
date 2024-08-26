package com.zenitech.imaapp.domain.usecases.sign_in

import com.zenitech.imaapp.data.auth.AuthenticationService

class IsAdminUseCase(
    private val repository: AuthenticationService,
) {

    operator fun invoke(): Boolean {
        return repository.isAdmin
    }

}