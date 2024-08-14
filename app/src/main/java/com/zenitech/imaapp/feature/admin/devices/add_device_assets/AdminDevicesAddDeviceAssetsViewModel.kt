package com.zenitech.imaapp.feature.admin.devices.add_device_assets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenitech.imaapp.domain.usecases.admin.AdminUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AdminDevicesAddDeviceAssetsState {
    data object Loading : AdminDevicesAddDeviceAssetsState()
    data class Error(val error: Throwable) : AdminDevicesAddDeviceAssetsState()
    data class Success(var deviceAssets: List<String>) : AdminDevicesAddDeviceAssetsState()
}

@HiltViewModel
class AdminDevicesAddDeviceAssetsViewModel @Inject constructor(
    private val adminOperations: AdminUseCases
): ViewModel() {

    private val _state = MutableStateFlow<AdminDevicesAddDeviceAssetsState>(
        AdminDevicesAddDeviceAssetsState.Loading
    )
    val state = _state.asStateFlow()

    fun loadDeviceAssets(){

        viewModelScope.launch {
            try {
                _state.value = AdminDevicesAddDeviceAssetsState.Loading
                val deviceAssets = adminOperations.loadAdminDeviceAddDeviceAssets().getOrThrow()
                _state.value = AdminDevicesAddDeviceAssetsState.Success(
                    deviceAssets = deviceAssets
                )
            } catch (e: Exception) {
                _state.value = AdminDevicesAddDeviceAssetsState.Error(e)
            }
        }

    }

}