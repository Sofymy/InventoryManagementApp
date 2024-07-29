package com.zenitech.imaapp.data.auth

interface AuthenticationService {
    suspend fun signInWithGoogle(idToken: String): Result<Unit>
    fun signOut()
    val hasUser: Boolean
}