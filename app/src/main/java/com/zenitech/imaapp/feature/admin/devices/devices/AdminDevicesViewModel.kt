package com.zenitech.imaapp.feature.admin.devices.devices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenitech.imaapp.domain.model.toDeviceSearchRequestUi
import com.zenitech.imaapp.domain.usecases.admin.AdminUseCases
import com.zenitech.imaapp.ui.model.DeviceSearchRequestUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AdminDevicesState {
    data class Loading(val isRefresh: Boolean) : AdminDevicesState()
    data class Error(val error: Throwable) : AdminDevicesState()
    data class Success(var adminDevices : List<DeviceSearchRequestUi>) : AdminDevicesState()
}

sealed class AdminDevicesUserEvent{
    data object Refresh: AdminDevicesUserEvent()
}

@HiltViewModel
class AdminDevicesViewModel @Inject constructor(
    private val adminOperations: AdminUseCases
): ViewModel() {

    private val _state = MutableStateFlow<AdminDevicesState>(AdminDevicesState.Loading(false))
    val state = _state.asStateFlow()

    fun onEvent(event: AdminDevicesUserEvent){
        when(event){
            AdminDevicesUserEvent.Refresh -> {
                refreshAdminDevices()
            }
        }
    }

    private fun refreshAdminDevices(){
        viewModelScope.launch {
            try {
                _state.value = AdminDevicesState.Loading(true)
                val adminDevices = adminOperations.loadAdminDevices().getOrThrow().map { it.toDeviceSearchRequestUi() }
                _state.value = AdminDevicesState.Success(
                    adminDevices = adminDevices
                )
            } catch (e: Exception) {
                _state.value = AdminDevicesState.Error(e)
            }
        }

    }

    fun loadAdminDevices(){
        viewModelScope.launch {
            try {
                _state.value = AdminDevicesState.Loading(false)
                val adminDevices = adminOperations.loadAdminDevices().getOrThrow().map { it.toDeviceSearchRequestUi() }
                _state.value = AdminDevicesState.Success(
                    adminDevices = adminDevices
                )
            } catch (e: Exception) {
                _state.value = AdminDevicesState.Error(e)
            }
        }

    }

}