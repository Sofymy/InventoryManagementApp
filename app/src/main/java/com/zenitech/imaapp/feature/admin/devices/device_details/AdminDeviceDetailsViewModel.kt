package com.zenitech.imaapp.feature.admin.devices.device_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenitech.imaapp.domain.model.toDeviceSearchRequestUi
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
    data class Success(var deviceDetailsList : DeviceSearchRequestUi) : AdminDeviceDetailsState()
}

@HiltViewModel
class AdminDeviceDetailsViewModel @Inject constructor(
    private val deviceDetailsOperations: DeviceDetailsUseCases
) : ViewModel() {

    private val _state = MutableStateFlow<AdminDeviceDetailsState>(AdminDeviceDetailsState.Loading)
    val state = _state.asStateFlow()

    fun loadDeviceDetails(inventoryId: String) {

        viewModelScope.launch {
            try {
                _state.value = AdminDeviceDetailsState.Loading
                val deviceDetails = deviceDetailsOperations.loadDeviceDetails(inventoryId).getOrThrow().toDeviceSearchRequestUi()
                _state.value = AdminDeviceDetailsState.Success(
                    deviceDetailsList = deviceDetails
                )
            } catch (e: Exception) {
                _state.value = AdminDeviceDetailsState.Error(e)
            }
        }

    }

}