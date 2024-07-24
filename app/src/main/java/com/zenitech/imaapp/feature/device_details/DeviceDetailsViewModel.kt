package com.zenitech.imaapp.feature.device_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenitech.imaapp.domain.model.DeviceAsset
import com.zenitech.imaapp.domain.model.DeviceSearchRequest
import com.zenitech.imaapp.domain.model.DeviceStatus
import com.zenitech.imaapp.domain.model.toDeviceSearchRequestUi
import com.zenitech.imaapp.domain.usecases.device_details.DeviceDetailsUseCases
import com.zenitech.imaapp.domain.usecases.my_devices.MyDevicesUseCases
import com.zenitech.imaapp.feature.my_devices.MyDevicesState
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

    fun loadDeviceDetails(){

        viewModelScope.launch {
            try {
                _state.value = DeviceDetailsState.Loading
                val deviceDetails = deviceDetailsOperations.loadDeviceDetails().getOrThrow().toDeviceSearchRequestUi()
                _state.value = DeviceDetailsState.Success(
                    deviceDetailsList = deviceDetails
                )
            } catch (e: Exception) {
                _state.value = DeviceDetailsState.Error(e)
            }
        }

    }

}