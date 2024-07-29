package com.zenitech.imaapp.feature.my_devices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenitech.imaapp.domain.model.asDeviceResponseUi
import com.zenitech.imaapp.domain.model.toDeviceSearchRequestUi
import com.zenitech.imaapp.domain.usecases.my_devices.MyDevicesUseCases
import com.zenitech.imaapp.ui.model.DeviceResponseUi
import com.zenitech.imaapp.ui.model.DeviceSearchRequestUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class MyDevicesState {
    data object Loading : MyDevicesState()
    data class Error(val error: Throwable) : MyDevicesState()
    data class Success(var myDeviceList : List<DeviceSearchRequestUi>) : MyDevicesState()
}

sealed class MyDevicesUserEvent {
    data class ChangeSortingOption(val sortingOption: SortingOption): MyDevicesUserEvent()
}

@HiltViewModel
class MyDevicesViewModel @Inject constructor(
    private val myDevicesOperations: MyDevicesUseCases
): ViewModel() {

    private val _state = MutableStateFlow<MyDevicesState>(MyDevicesState.Loading)
    val state = _state.asStateFlow()

    fun onEvent(event: MyDevicesUserEvent){
        when(event){
            is MyDevicesUserEvent.ChangeSortingOption -> {
                sortDevices(event.sortingOption)
            }
        }
    }

    fun loadMyDevices(){

        viewModelScope.launch {
            try {
                _state.value = MyDevicesState.Loading
                val myDevices = myDevicesOperations.loadMyDevices().getOrThrow().map { it.toDeviceSearchRequestUi() }
                _state.value = MyDevicesState.Success(
                    myDeviceList = myDevices
                )
            } catch (e: Exception) {
                _state.value = MyDevicesState.Error(e)
            }
        }

    }

    private fun sortDevices(sortingOption: SortingOption) {
        _state.update { currentState ->
            if (currentState is MyDevicesState.Success) {
                val sortedList = when (sortingOption) {
                    SortingOption.Manufacturer -> currentState.myDeviceList.sortedBy { it.manufacturer }
                    SortingOption.Asset -> currentState.myDeviceList.sortedBy { it.asset.name }
                }
                currentState.copy(myDeviceList = sortedList)
            } else {
                currentState
            }
        }
    }
}