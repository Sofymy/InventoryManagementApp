package com.zenitech.imaapp.feature.request_test_device

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenitech.imaapp.domain.usecases.request_test_device.RequestTestDeviceUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class RequestTestDeviceManufacturerState {
    data object Loading : RequestTestDeviceManufacturerState()
    data class Error(val error: Throwable) : RequestTestDeviceManufacturerState()
    data class Success(var testDeviceManufacturers: List<String>) : RequestTestDeviceManufacturerState()
}

@HiltViewModel
class RequestTestDeviceManufacturerViewModel @Inject constructor(
    private val testDeviceManufacturersOperations: RequestTestDeviceUseCases
): ViewModel() {

    private val _state = MutableStateFlow<RequestTestDeviceManufacturerState>(RequestTestDeviceManufacturerState.Loading)
    val state = _state.asStateFlow()

    fun loadTestDeviceManufacturers(){

        viewModelScope.launch {
            try {
                _state.value = RequestTestDeviceManufacturerState.Loading
                val testDeviceTypes = testDeviceManufacturersOperations.loadTestDeviceManufacturers().getOrThrow()
                _state.value = RequestTestDeviceManufacturerState.Success(
                    testDeviceManufacturers = testDeviceTypes
                )
            } catch (e: Exception) {
                _state.value = RequestTestDeviceManufacturerState.Error(e)
            }
        }

    }

}