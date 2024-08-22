package com.zenitech.imaapp.feature.admin.devices.device_types

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenitech.imaapp.domain.usecases.admin.AdminUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AdminDevicesDeviceTypeState {
    data object Loading : AdminDevicesDeviceTypeState()
    data class Error(val error: Throwable) : AdminDevicesDeviceTypeState()
    data class Success(var deviceTypes : List<String>) : AdminDevicesDeviceTypeState()
}

@HiltViewModel
class AdminDevicesDeviceTypesViewModel @Inject constructor(
    private val adminOperations: AdminUseCases
): ViewModel() {

    private val _state = MutableStateFlow<AdminDevicesDeviceTypeState>(
        AdminDevicesDeviceTypeState.Loading
    )
    val state = _state.asStateFlow()

    fun loadDeviceTypes(){

        viewModelScope.launch {
            try {
                _state.value = AdminDevicesDeviceTypeState.Loading
                val deviceTypes = adminOperations.loadAdminAddDeviceTypes().getOrThrow()
                _state.value = AdminDevicesDeviceTypeState.Success(
                    deviceTypes = deviceTypes
                )
            } catch (e: Exception) {
                _state.value = AdminDevicesDeviceTypeState.Error(e)
            }
        }

    }

}