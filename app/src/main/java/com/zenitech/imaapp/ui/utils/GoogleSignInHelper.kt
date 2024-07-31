package com.zenitech.imaapp.ui.utils

import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption

object GoogleSignInHelper {

    private val signInWithGoogleOption = GetSignInWithGoogleOption.Builder("756383197869-p01ve0lvqqplfs455hvlc78jdhfg7hrr.apps.googleusercontent.com")
        .setHostedDomainFilter("zenitech.co.uk")
        .build()

    val request: GetCredentialRequest = GetCredentialRequest.Builder()
        .addCredentialOption(signInWithGoogleOption)
        .build()

}