package com.zenitech.imaapp.ui.utils

import android.content.Context
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity

object GoogleSignInHelper {

    fun getGoogleSignInClient(context: Context) = Identity.getSignInClient(context)

    fun getGoogleSignInRequest() = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setFilterByAuthorizedAccounts(false)
                .setSupported(true)
                .setServerClientId("756383197869-p01ve0lvqqplfs455hvlc78jdhfg7hrr.apps.googleusercontent.com")
                .build()
        ).build()

}