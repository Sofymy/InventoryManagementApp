package com.zenitech.imaapp.domain.usecases.sign_in

import androidx.credentials.GetCredentialResponse
import com.zenitech.imaapp.data.auth.AuthenticationService
import java.io.IOException

class SignInWithGoogleUseCase(
    private val repository: AuthenticationService,
) {

    suspend operator fun invoke(response: GetCredentialResponse): Result<String> {
        return try {
            repository.signInWithGoogle(response)
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

}
