package com.zenitech.imaapp.feature.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenitech.imaapp.domain.usecases.sign_in.SignInUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class SignInState {
    data object Init : SignInState()
    data object Loading : SignInState()
    data class Error(val error: Throwable) : SignInState()
    data object Success : SignInState()
    data class IsSignedIn(val signedIn: Boolean) : SignInState()
}

sealed class SignInUserEvent {
    data class SignIn(val idToken: String) : SignInUserEvent()
    data object HasUser: SignInUserEvent()
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
                signInWithGoogle(event.idToken)
            }
            SignInUserEvent.SignOut -> {
                signOut()
            }
        }
    }

    private fun hasUser() {
        viewModelScope.launch {
            try {
                _state.value = SignInState.Loading
                val result = signInOperations.hasUser()
                if(result){
                    _state.value = SignInState.Success
                }
                else
                    _state.value = SignInState.Init
            } catch (e: Exception) {
                _state.value = SignInState.Error(e)
            }
        }
    }

    private fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            try {
                _state.value = SignInState.Loading
                val result = signInOperations.signInWithGoogle(idToken)
                if(result.isSuccess){
                    _state.value = SignInState.Success
                }
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