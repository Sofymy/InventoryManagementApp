package com.zenitech.imaapp.feature.admin.devices.device_assets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenitech.imaapp.domain.usecases.admin.AdminUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AdminDevicesDeviceAssetsState {
    data object Loading : AdminDevicesDeviceAssetsState()
    data class Error(val error: Throwable) : AdminDevicesDeviceAssetsState()
    data class Success(var deviceAssets: List<String>) : AdminDevicesDeviceAssetsState()
}

@HiltViewModel
class AdminDevicesDeviceAssetsViewModel @Inject constructor(
    private val adminOperations: AdminUseCases
): ViewModel() {

    private val _state = MutableStateFlow<AdminDevicesDeviceAssetsState>(
        AdminDevicesDeviceAssetsState.Loading
    )
    val state = _state.asStateFlow()

    fun loadDeviceAssets(){

        viewModelScope.launch {
            try {
                _state.value = AdminDevicesDeviceAssetsState.Loading
                val deviceAssets = adminOperations.loadAdminDeviceAddDeviceAssets().getOrThrow()
                _state.value = AdminDevicesDeviceAssetsState.Success(
                    deviceAssets = deviceAssets
                )
            } catch (e: Exception) {
                _state.value = AdminDevicesDeviceAssetsState.Error(e)
            }
        }

    }

}