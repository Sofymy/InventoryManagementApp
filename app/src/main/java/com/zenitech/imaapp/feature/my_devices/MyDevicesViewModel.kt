package com.zenitech.imaapp.feature.my_devices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenitech.imaapp.domain.usecases.my_devices.MyDevicesUseCases
import com.zenitech.imaapp.ui.model.DeviceResponseUi
import com.zenitech.imaapp.ui.model.asDeviceResponseUi
import com.zenitech.imaapp.ui.model.asDeviceTypeUi
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
    data class Success(var myDeviceList : List<DeviceResponseUi>) : MyDevicesState()
}

@HiltViewModel
class MyDevicesViewModel @Inject constructor(
    private val myDevicesOperations: MyDevicesUseCases
): ViewModel() {

    private val _state = MutableStateFlow<MyDevicesState>(MyDevicesState.Loading)
    val state = _state.asStateFlow()

    fun loadMyDevices(){

        viewModelScope.launch {
            try {
                _state.value = MyDevicesState.Loading
                val myDevices = myDevicesOperations.loadMyDevices().getOrThrow().map { it.asDeviceResponseUi() }
                _state.value = MyDevicesState.Success(
                    myDeviceList = myDevices
                )
            } catch (e: Exception) {
                _state.value = MyDevicesState.Error(e)
            }
        }

    }


    private val _sortingOption = MutableStateFlow(SortingOption.Name)
    val sortingOption: StateFlow<SortingOption> = _sortingOption.asStateFlow()


    fun setSortingOption(option: SortingOption) {
        _sortingOption.value = option
        sortDevices()
    }

    private fun sortDevices() {
        _state.update { currentState ->
            if (currentState is MyDevicesState.Success) {
                val sortedList = when (_sortingOption.value) {
                    SortingOption.Name -> currentState.myDeviceList.sortedBy { it.inventoryNumber }
                    SortingOption.Type -> currentState.myDeviceList.sortedBy { it.assetName.label }
                }
                currentState.copy(myDeviceList = sortedList)
            } else {
                currentState
            }
        }
    }
}

