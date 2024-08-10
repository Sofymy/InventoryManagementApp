package com.zenitech.imaapp.feature.my_devices.device_details

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


sealed class DeviceDetailsState {
    data object Loading : DeviceDetailsState()
    data class Error(val error: Throwable) : DeviceDetailsState()
    data class Success(var deviceDetailsList : DeviceSearchRequestUi) : DeviceDetailsState()
}

@HiltViewModel
class DeviceDetailsViewModel @Inject constructor(
    private val deviceDetailsOperations: DeviceDetailsUseCases
) : ViewModel() {

    private val _state = MutableStateFlow<DeviceDetailsState>(DeviceDetailsState.Loading)
    val state = _state.asStateFlow()

    fun loadDeviceDetails(inventoryId: String) {

        viewModelScope.launch {
            try {
                _state.value = DeviceDetailsState.Loading
                val deviceDetails = deviceDetailsOperations.loadDeviceDetails(inventoryId).getOrThrow().toDeviceSearchRequestUi()
                _state.value = DeviceDetailsState.Success(
                    deviceDetailsList = deviceDetails
                )
            } catch (e: Exception) {
                _state.value = DeviceDetailsState.Error(e)
            }
        }

    }

}