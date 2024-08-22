package com.zenitech.imaapp.feature.admin.devices.device_manufacturers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenitech.imaapp.domain.usecases.admin.AdminUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AdminDevicesDeviceManufacturersState {
    data object Loading : AdminDevicesDeviceManufacturersState()
    data class Error(val error: Throwable) : AdminDevicesDeviceManufacturersState()
    data class Success(var deviceManufacturers: List<String>) : AdminDevicesDeviceManufacturersState()
}

@HiltViewModel
class AdminDevicesDeviceManufacturersViewModel @Inject constructor(
    private val adminOperations: AdminUseCases
): ViewModel() {

    private val _state = MutableStateFlow<AdminDevicesDeviceManufacturersState>(
        AdminDevicesDeviceManufacturersState.Loading
    )
    val state = _state.asStateFlow()

    fun loadDeviceManufacturers(){

        viewModelScope.launch {
            try {
                _state.value = AdminDevicesDeviceManufacturersState.Loading
                val deviceManufacturers = adminOperations.loadAdminAddDeviceManufacturers().getOrThrow()
                _state.value = AdminDevicesDeviceManufacturersState.Success(
                    deviceManufacturers = deviceManufacturers
                )
            } catch (e: Exception) {
                _state.value = AdminDevicesDeviceManufacturersState.Error(e)
            }
        }

    }

}