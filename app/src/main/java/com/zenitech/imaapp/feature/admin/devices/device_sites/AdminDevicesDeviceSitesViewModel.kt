package com.zenitech.imaapp.feature.admin.devices.device_sites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenitech.imaapp.domain.usecases.admin.AdminUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AdminDevicesDeviceSitesState {
    data object Loading : AdminDevicesDeviceSitesState()
    data class Error(val error: Throwable) : AdminDevicesDeviceSitesState()
    data class Success(var deviceSites : List<String>) : AdminDevicesDeviceSitesState()
}

@HiltViewModel
class AdminDevicesDeviceSitesViewModel @Inject constructor(
    private val adminOperations: AdminUseCases
): ViewModel() {

    private val _state = MutableStateFlow<AdminDevicesDeviceSitesState>(
        AdminDevicesDeviceSitesState.Loading
    )
    val state = _state.asStateFlow()

    fun loadDeviceSites(){

        viewModelScope.launch {
            try {
                _state.value = AdminDevicesDeviceSitesState.Loading
                val deviceSites = adminOperations.loadAdminAddDevicesSites().getOrThrow()
                _state.value = AdminDevicesDeviceSitesState.Success(
                    deviceSites = deviceSites
                )
            } catch (e: Exception) {
                _state.value = AdminDevicesDeviceSitesState.Error(e)
            }
        }

    }

}