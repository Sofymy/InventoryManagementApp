package com.zenitech.imaapp.feature.admin.devices.add_device

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenitech.imaapp.domain.usecases.admin.AdminUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AdminDevicesAddDeviceManufacturersState {
    data object Loading : AdminDevicesAddDeviceManufacturersState()
    data class Error(val error: Throwable) : AdminDevicesAddDeviceManufacturersState()
    data class Success(var deviceManufacturers: List<String>) : AdminDevicesAddDeviceManufacturersState()
}

@HiltViewModel
class AdminDevicesAddDeviceManufacturersViewModel @Inject constructor(
    private val adminOperations: AdminUseCases
): ViewModel() {

    private val _state = MutableStateFlow<AdminDevicesAddDeviceManufacturersState>(
        AdminDevicesAddDeviceManufacturersState.Loading
    )
    val state = _state.asStateFlow()

    fun loadDeviceManufacturers(){

        viewModelScope.launch {
            try {
                _state.value = AdminDevicesAddDeviceManufacturersState.Loading
                val deviceManufacturers = adminOperations.loadAdminAddDeviceManufacturers().getOrThrow()
                _state.value = AdminDevicesAddDeviceManufacturersState.Success(
                    deviceManufacturers = deviceManufacturers
                )
            } catch (e: Exception) {
                _state.value = AdminDevicesAddDeviceManufacturersState.Error(e)
            }
        }

    }

}