package com.zenitech.imaapp.data.auth

import androidx.credentials.GetCredentialResponse

interface AuthenticationService {

    fun signOut()
    val isAdmin: Boolean
    val hasUser: Boolean
    suspend fun signInWithGoogle(result: GetCredentialResponse): Result<String>
}