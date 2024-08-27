package com.zenitech.imaapp.ui.utils

import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption

object GoogleSignInHelper {

    private val signInWithGoogleOption = GetSignInWithGoogleOption.Builder(Constants.SERVER_CLIENT_ID)
        .setHostedDomainFilter("zenitech.co.uk")
        .build()

    val request: GetCredentialRequest = GetCredentialRequest.Builder()
        .addCredentialOption(signInWithGoogleOption)
        .build()

}