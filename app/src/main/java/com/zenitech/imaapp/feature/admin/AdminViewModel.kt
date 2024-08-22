package com.zenitech.imaapp.feature.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenitech.imaapp.data.repository.AdminRepository
import com.zenitech.imaapp.data.repository.MyDevicesRepository
import com.zenitech.imaapp.domain.model.toTestDeviceRequestUi
import com.zenitech.imaapp.domain.usecases.admin.AdminUseCases
import com.zenitech.imaapp.feature.admin.manage_requests.ManageRequestsState
import com.zenitech.imaapp.ui.model.TestDeviceRequestUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AdminState {
    data object Loading : AdminState()
    data class Error(val error: Throwable) : AdminState()
    data class Success(var requestsSize: Int) : AdminState()
}

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val adminOperations: AdminUseCases
): ViewModel() {

    private val _state = MutableStateFlow<AdminState>(AdminState.Loading)
    val state = _state.asStateFlow()

    fun loadRequests(){
        viewModelScope.launch {
            try {
                _state.value = AdminState.Loading
                val requests = adminOperations.loadRequests().getOrThrow().size
                _state.value = AdminState.Success(
                    requestsSize = requests
                )
            } catch (e: Exception) {
                _state.value = AdminState.Error(e)
            }
        }

    }
}