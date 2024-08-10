package com.zenitech.imaapp.feature.admin.devices.add_device

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenitech.imaapp.domain.usecases.admin.AdminUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AdminDevicesAddDeviceTypeState {
    data object Loading : AdminDevicesAddDeviceTypeState()
    data class Error(val error: Throwable) : AdminDevicesAddDeviceTypeState()
    data class Success(var deviceTypes : List<String>) : AdminDevicesAddDeviceTypeState()
}

@HiltViewModel
class AdminDevicesAddDeviceTypesViewModel @Inject constructor(
    private val adminOperations: AdminUseCases
): ViewModel() {

    private val _state = MutableStateFlow<AdminDevicesAddDeviceTypeState>(
        AdminDevicesAddDeviceTypeState.Loading
    )
    val state = _state.asStateFlow()

    fun loadDeviceTypes(){

        viewModelScope.launch {
            try {
                _state.value = AdminDevicesAddDeviceTypeState.Loading
                val deviceTypes = adminOperations.loadAdminAddDeviceTypes().getOrThrow()
                _state.value = AdminDevicesAddDeviceTypeState.Success(
                    deviceTypes = deviceTypes
                )
            } catch (e: Exception) {
                _state.value = AdminDevicesAddDeviceTypeState.Error(e)
            }
        }

    }

}