package com.zenitech.imaapp.feature.admin.devices.device_info.device_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenitech.imaapp.domain.model.toDeviceSearchRequestUi
import com.zenitech.imaapp.domain.model.toHistoryResponseUi
import com.zenitech.imaapp.domain.usecases.admin.AdminUseCases
import com.zenitech.imaapp.domain.usecases.device_details.DeviceDetailsUseCases
import com.zenitech.imaapp.feature.admin.devices.device_info.device_details.AdminDeviceDetailsState
import com.zenitech.imaapp.ui.model.DeviceSearchRequestUi
import com.zenitech.imaapp.ui.model.HistoryResponseUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class AdminDeviceHistoryState {
    data object Loading : AdminDeviceHistoryState()
    data class Error(val error: Throwable) : AdminDeviceHistoryState()
    data class Success(var deviceHistory: List<HistoryResponseUi>) : AdminDeviceHistoryState()
}

@HiltViewModel
class AdminDeviceHistoryViewModel @Inject constructor(
    private val adminOperations: AdminUseCases,
) : ViewModel() {

    private val _state = MutableStateFlow<AdminDeviceHistoryState>(AdminDeviceHistoryState.Loading)
    val state = _state.asStateFlow()

    fun loadDeviceHistory(inventoryId: String) {

        viewModelScope.launch {
            try {
                _state.value = AdminDeviceHistoryState.Loading
                val deviceHistory = adminOperations.loadDeviceHistory(inventoryId).getOrThrow().map { it.toHistoryResponseUi() }
                _state.value = AdminDeviceHistoryState.Success(
                    deviceHistory = deviceHistory
                )
            } catch (e: Exception) {
                _state.value = AdminDeviceHistoryState.Error(e)
            }
        }

    }
}