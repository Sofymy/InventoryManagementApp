package com.zenitech.imaapp.data.auth

import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialResponse
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class AuthenticationServiceImpl(
    private val firebaseAuth: FirebaseAuth
): AuthenticationService {

    override val isAdmin: Boolean get() = true
    override val hasUser: Boolean get() = firebaseAuth.currentUser != null

    override suspend fun signInWithGoogle(result: GetCredentialResponse): Result<String> {
        return suspendCancellableCoroutine { cont ->
            when (val credential = result.credential) {
                is CustomCredential -> {
                    if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                        try {
                            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                            googleIdTokenCredential.idToken.let { idToken ->
                                val authCredential = GoogleAuthProvider.getCredential(idToken, null)
                                firebaseAuth.signInWithCredential(authCredential)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            cont.resume(Result.success(idToken))
                                        } else {
                                            cont.resume(Result.failure(Throwable(task.exception)))
                                        }
                                    }
                            }
                        } catch (e: GoogleIdTokenParsingException) {
                            cont.resume(Result.failure(e))
                        }
                    }
                }
                else -> {
                    cont.resume(Result.failure(Throwable()))
                }
            }
        }

    }

    override fun signOut() {
        firebaseAuth.signOut()
    }
}