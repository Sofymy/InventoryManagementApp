package com.zenitech.imaapp.feature.sign_in

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenitech.imaapp.domain.usecases.sign_in.SignInUseCases
import com.zenitech.imaapp.ui.utils.GoogleSignInHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class SignInState {
    data object Init : SignInState()
    data object Loading : SignInState()
    data class Error(val error: Throwable) : SignInState()
    data class Success(val isAdmin: Boolean? = false) : SignInState()
}

sealed class SignInUserEvent {
    data class SignIn(val context: Context) : SignInUserEvent()
    data object HasUser: SignInUserEvent()
    data object IsAdmin: SignInUserEvent()
    data object SignOut: SignInUserEvent()
}

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInOperations: SignInUseCases
) : ViewModel() {

    private val _state = MutableStateFlow<SignInState>(SignInState.Init)
    val state = _state.asStateFlow()

    fun onEvent(event: SignInUserEvent){
        when(event){
            is SignInUserEvent.HasUser -> {
                hasUser()
            }
            is SignInUserEvent.SignIn -> {
                signInWithGoogle(event.context)
            }
            is SignInUserEvent.SignOut -> {
                signOut()
            }
            is SignInUserEvent.IsAdmin -> {
                isAdmin()
            }
        }
    }

    private fun isAdmin() {
        viewModelScope.launch {
            try {
                _state.value = SignInState.Loading
                val result = signInOperations.isAdmin()
                if(result){
                    @Suppress("KotlinConstantConditions")
                    _state.value = SignInState.Success(isAdmin = result)
                }
                else
                    _state.value = SignInState.Init
            } catch (e: Exception) {
                _state.value = SignInState.Error(e)
            }
        }
    }

    private fun hasUser() {
        viewModelScope.launch {
            try {
                _state.value = SignInState.Loading
                val result = signInOperations.hasUser()
                if(result){
                    _state.value = SignInState.Success()
                }
                else
                    _state.value = SignInState.Init
            } catch (e: Exception) {
                _state.value = SignInState.Error(e)
            }
        }
    }

    private fun signInWithGoogle(context: Context) {
        val credentialManager = CredentialManager.create(context)

        viewModelScope.launch {
            _state.value = SignInState.Loading
            try {
                val response = credentialManager.getCredential(
                    request = GoogleSignInHelper.request,
                    context = context
                )
                val result = signInOperations.signInWithGoogle(response)
                _state.value = if (result.isSuccess) {
                    SignInState.Success()
                } else {
                    SignInState.Error(Throwable(result.exceptionOrNull()))
                }
            } catch (e: GetCredentialCancellationException) {
                _state.value = SignInState.Error(e)
            } catch (e: GetCredentialException) {
                _state.value = SignInState.Error(e)
            } catch (e: Exception) {
                _state.value = SignInState.Error(e)
            }
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            try {
                _state.value = SignInState.Loading
                signInOperations.signOut()
                _state.value = SignInState.Init
            } catch (e: Exception) {
                _state.value = SignInState.Error(e)
            }
        }

    }
}