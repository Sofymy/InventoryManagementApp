package com.zenitech.imaapp.feature.admin.devices.add_device_sites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenitech.imaapp.domain.usecases.admin.AdminUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AdminDevicesAddDeviceSitesState {
    data object Loading : AdminDevicesAddDeviceSitesState()
    data class Error(val error: Throwable) : AdminDevicesAddDeviceSitesState()
    data class Success(var deviceSites : List<String>) : AdminDevicesAddDeviceSitesState()
}

@HiltViewModel
class AdminDevicesAddDeviceSitesViewModel @Inject constructor(
    private val adminOperations: AdminUseCases
): ViewModel() {

    private val _state = MutableStateFlow<AdminDevicesAddDeviceSitesState>(
        AdminDevicesAddDeviceSitesState.Loading
    )
    val state = _state.asStateFlow()

    fun loadDeviceSites(){

        viewModelScope.launch {
            try {
                _state.value = AdminDevicesAddDeviceSitesState.Loading
                val deviceSites = adminOperations.loadAdminAddDevicesSites().getOrThrow()
                _state.value = AdminDevicesAddDeviceSitesState.Success(
                    deviceSites = deviceSites
                )
            } catch (e: Exception) {
                _state.value = AdminDevicesAddDeviceSitesState.Error(e)
            }
        }

    }

}