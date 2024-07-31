package com.zenitech.imaapp.feature.request_test_device

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenitech.imaapp.domain.usecases.request_test_device.RequestTestDeviceUseCases
import com.zenitech.imaapp.ui.model.RequestTestDeviceUi
import com.zenitech.imaapp.ui.utils.validation.ValidateState
import com.zenitech.imaapp.ui.utils.validation.ValidationError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class RequestTestDeviceUserEvent {
    data class ChangeDeviceType(val text: String) : RequestTestDeviceUserEvent()
    data class ChangeDeviceManufacturer(val text: String) : RequestTestDeviceUserEvent()
    data class ChangeRequestDate(val date: String) : RequestTestDeviceUserEvent()
    data class ChangeReturnDate(val date: String) : RequestTestDeviceUserEvent()
    data class ChangeAdditionalRequests(val text: String) : RequestTestDeviceUserEvent()
    data object SaveRequest : RequestTestDeviceUserEvent()
    data object SavedSuccessfully : RequestTestDeviceUserEvent()
}

sealed class RequestTestDeviceState {
    data object Loading : RequestTestDeviceState()
    data object Success : RequestTestDeviceState()
    data class Failure(val error: List<ValidationError?>) : RequestTestDeviceState()
}

@HiltViewModel
class RequestTestDeviceViewModel @Inject constructor(
    private val requestTestDeviceOperations: RequestTestDeviceUseCases
) : ViewModel() {

    private val _state = MutableStateFlow<RequestTestDeviceState>(RequestTestDeviceState.Loading)
    val state = _state.asStateFlow()

    private var uiState by mutableStateOf(RequestTestDeviceUi())

    fun onEvent(event: RequestTestDeviceUserEvent) {
        when (event) {
            is RequestTestDeviceUserEvent.ChangeAdditionalRequests -> {
                uiState = uiState.copy(additionalRequests = event.text)
            }
            is RequestTestDeviceUserEvent.ChangeDeviceManufacturer -> {
                uiState = uiState.copy(manufacturer = event.text)
            }
            is RequestTestDeviceUserEvent.ChangeDeviceType -> {
                uiState = uiState.copy(type = event.text)
            }
            is RequestTestDeviceUserEvent.ChangeRequestDate -> {
                uiState = uiState.copy(requestDate = event.date)
            }
            is RequestTestDeviceUserEvent.ChangeReturnDate -> {
                uiState = uiState.copy(returnDate = event.date)
            }
            RequestTestDeviceUserEvent.SaveRequest -> {
                onSave()
            }
            RequestTestDeviceUserEvent.SavedSuccessfully -> {
                resetForm()
            }
        }
    }

    private fun onSave() {
        val stateValidator = ValidateState(RequestTestDeviceUi::class)
        val errors = stateValidator.validate(uiState)

        viewModelScope.launch {
            if (errors.isEmpty()) {
                try {
                    requestTestDeviceOperations.saveTestDeviceRequestUseCase()
                    _state.value = RequestTestDeviceState.Success
                    Log.d(LOG_TAG, "Save request successful")
                } catch (e: Exception) {
                    Log.e(LOG_TAG, "Save request failed", e)
                    _state.value = RequestTestDeviceState.Failure(listOf(ValidationError(message = e.message.toString())))
                }
            } else {
                _state.value = RequestTestDeviceState.Failure(errors)
            }
        }
    }

    private fun resetForm() {
        uiState = RequestTestDeviceUi()
        _state.value = RequestTestDeviceState.Success
    }

    companion object {
        private const val LOG_TAG = "RequestTestDeviceVM"
    }
}
