package com.zenitech.imaapp.feature.admin.devices.device_info.device_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenitech.imaapp.domain.model.toDeviceSearchRequest
import com.zenitech.imaapp.domain.model.toDeviceSearchRequestUi
import com.zenitech.imaapp.domain.usecases.admin.AdminUseCases
import com.zenitech.imaapp.domain.usecases.device_details.DeviceDetailsUseCases
import com.zenitech.imaapp.ui.model.DeviceSearchRequestUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AdminDeviceDetailsState {
    data object Loading : AdminDeviceDetailsState()
    data class Error(val error: Throwable) : AdminDeviceDetailsState()
    data class Success(var device: DeviceSearchRequestUi) : AdminDeviceDetailsState()
}

sealed class AdminDevicesUserEvent{
    data class DeleteDevice(val device: DeviceSearchRequestUi) : AdminDevicesUserEvent()
    data class SaveModifications(val device: DeviceSearchRequestUi) : AdminDevicesUserEvent()
}

@HiltViewModel
class AdminDeviceDetailsViewModel @Inject constructor(
    private val adminOperations: AdminUseCases,
    private val deviceDetailsOperations: DeviceDetailsUseCases
) : ViewModel() {

    private val _state = MutableStateFlow<AdminDeviceDetailsState>(AdminDeviceDetailsState.Loading)
    val state = _state.asStateFlow()

    fun onEvent(event: AdminDevicesUserEvent){
        when(event){
            is AdminDevicesUserEvent.DeleteDevice -> {
                deleteDevice(event.device)
            }

            is AdminDevicesUserEvent.SaveModifications -> {
                saveModifications(event.device)
            }
        }
    }

    private fun saveModifications(device: DeviceSearchRequestUi) {

        viewModelScope.launch {
            try {
                adminOperations.saveModifications(device.toDeviceSearchRequest())
            } catch (e: Exception) {
                _state.value = AdminDeviceDetailsState.Error(e)
            }
        }

    }

    private fun deleteDevice(device: DeviceSearchRequestUi) {

        viewModelScope.launch {
            try {
                adminOperations.deleteDevice(device.toDeviceSearchRequest())
            } catch (e: Exception) {
                _state.value = AdminDeviceDetailsState.Error(e)
            }
        }
    }

    fun loadDeviceDetails(inventoryId: String) {

        viewModelScope.launch {
            try {
                _state.value = AdminDeviceDetailsState.Loading
                val deviceDetails = deviceDetailsOperations.loadDeviceDetails(inventoryId).getOrThrow().toDeviceSearchRequestUi()
                _state.value = AdminDeviceDetailsState.Success(
                    device = deviceDetails
                )
            } catch (e: Exception) {
                _state.value = AdminDeviceDetailsState.Error(e)
            }
        }

    }

}