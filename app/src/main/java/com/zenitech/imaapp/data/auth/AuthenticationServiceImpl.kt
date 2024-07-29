package com.zenitech.imaapp.data.auth

import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class AuthenticationServiceImpl(
    private val firebaseAuth: FirebaseAuth
): AuthenticationService {

    override val hasUser: Boolean get() = firebaseAuth.currentUser != null

    override suspend fun signInWithGoogle(idToken: String): Result<Unit> {
        GoogleSignInOptions.DEFAULT_SIGN_IN
        return suspendCancellableCoroutine { cont ->
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        cont.resume(Result.success(Unit))
                    } else {
                        cont.resume(Result.failure(task.exception ?: Exception("Unknown error")))
                    }
                }
        }
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }
}