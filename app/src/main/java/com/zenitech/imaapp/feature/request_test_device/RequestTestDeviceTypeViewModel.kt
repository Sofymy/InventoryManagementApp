package com.zenitech.imaapp.feature.request_test_device

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenitech.imaapp.domain.model.toDeviceSearchRequestUi
import com.zenitech.imaapp.domain.usecases.my_devices.MyDevicesUseCases
import com.zenitech.imaapp.domain.usecases.request_test_device.RequestTestDeviceUseCases
import com.zenitech.imaapp.feature.my_devices.SortingOption
import com.zenitech.imaapp.ui.model.DeviceSearchRequestUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class RequestTestDeviceTypeState {
    data object Loading : RequestTestDeviceTypeState()
    data class Error(val error: Throwable) : RequestTestDeviceTypeState()
    data class Success(var testDeviceTypes : List<String>) : RequestTestDeviceTypeState()
}

@HiltViewModel
class RequestTestDeviceTypeViewModel @Inject constructor(
    private val testDeviceTypesOperations: RequestTestDeviceUseCases
): ViewModel() {

    private val _state = MutableStateFlow<RequestTestDeviceTypeState>(RequestTestDeviceTypeState.Loading)
    val state = _state.asStateFlow()

    fun loadTestDeviceTypes(){

        viewModelScope.launch {
            try {
                _state.value = RequestTestDeviceTypeState.Loading
                val testDeviceTypes = testDeviceTypesOperations.loadTestDeviceTypes().getOrThrow()
                _state.value = RequestTestDeviceTypeState.Success(
                    testDeviceTypes = testDeviceTypes
                )
            } catch (e: Exception) {
                _state.value = RequestTestDeviceTypeState.Error(e)
            }
        }

    }

}