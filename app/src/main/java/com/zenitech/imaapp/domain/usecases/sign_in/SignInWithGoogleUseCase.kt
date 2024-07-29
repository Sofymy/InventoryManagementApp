package com.zenitech.imaapp.domain.usecases.sign_in

import com.zenitech.imaapp.data.auth.AuthenticationService
import java.io.IOException

class SignInWithGoogleUseCase(
    private val repository: AuthenticationService,
) {

    suspend operator fun invoke(idToken: String): Result<Unit> {
        return try {
            repository.signInWithGoogle(idToken)
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

}
