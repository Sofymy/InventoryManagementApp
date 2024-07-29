package com.zenitech.imaapp.feature.request_test_device

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LOG_TAG
import com.zenitech.imaapp.domain.usecases.request_test_device.RequestTestDeviceUseCases
import com.zenitech.imaapp.ui.model.RequestTestDeviceUi
import com.zenitech.imaapp.ui.model.UiEvent
import com.zenitech.imaapp.ui.utils.validation.ValidateState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class RequestTestDeviceUserEvent {
    data class ChangeDeviceType(val text: String) : RequestTestDeviceUserEvent()
    data class ChangeDeviceManufacturer(val text: String) : RequestTestDeviceUserEvent()
    data class ChangeRequestDate(val date: String) : RequestTestDeviceUserEvent()
    data class ChangeReturnDate(val date: String) : RequestTestDeviceUserEvent()
    data class ChangeAdditionalRequests(val text: String) : RequestTestDeviceUserEvent()
    data object SaveRequest: RequestTestDeviceUserEvent()
    data object SavedSuccessfully: RequestTestDeviceUserEvent()

}

data class RequestTestDeviceState(
    val request: RequestTestDeviceUi = RequestTestDeviceUi()
)

@HiltViewModel
class RequestTestDeviceViewModel @Inject constructor(
    private val requestTestDeviceOperations: RequestTestDeviceUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(RequestTestDeviceState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: RequestTestDeviceUserEvent) {
        when(event) {
            is RequestTestDeviceUserEvent.ChangeAdditionalRequests -> {
                val newValue = event.text
                _state.update { it.copy(
                    request = it.request.copy(additionalRequests = newValue)
                ) }
            }
            is RequestTestDeviceUserEvent.ChangeDeviceManufacturer -> {
                val newValue = event.text
                _state.update { it.copy(
                    request = it.request.copy(manufacturer = newValue)
                ) }
            }
            is RequestTestDeviceUserEvent.ChangeDeviceType -> {
                val newValue = event.text
                _state.update { it.copy(
                    request = it.request.copy(type = newValue)
                ) }
            }
            is RequestTestDeviceUserEvent.ChangeRequestDate -> {
                val newValue = event.date
                _state.update { it.copy(
                    request = it.request.copy(requestDate = newValue)
                ) }
            }
            is RequestTestDeviceUserEvent.ChangeReturnDate -> {
                val newValue = event.date
                _state.update { it.copy(
                    request = it.request.copy(returnDate = newValue)
                ) }
            }
            is RequestTestDeviceUserEvent.SavedSuccessfully -> {
                _state.update {
                    it.copy(
                        request = it.request.copy(
                            type = "",
                            manufacturer = "",
                            requestDate = "",
                            returnDate = "",
                            additionalRequests = "",
                            error = null
                        )
                    )
                }
            }

            RequestTestDeviceUserEvent.SaveRequest -> {
                onSave()
            }
        }
    }

    private fun onSave() {
        val stateValidator = ValidateState(RequestTestDeviceUi::class);
        val errors = stateValidator.validate(state.value.request)

        viewModelScope.launch {
            if (errors.isEmpty()) {
                requestTestDeviceOperations.saveTestDeviceRequestUseCase()
                _uiEvent.send(UiEvent.Success)
            } else {
                _uiEvent.send(UiEvent.Failure(errors))
            }
        }
    }
}