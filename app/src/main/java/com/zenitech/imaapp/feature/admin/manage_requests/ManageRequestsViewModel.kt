package com.zenitech.imaapp.feature.admin.manage_requests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenitech.imaapp.domain.model.toTestDeviceRequestUi
import com.zenitech.imaapp.domain.usecases.admin.AdminUseCases
import com.zenitech.imaapp.ui.model.TestDeviceRequestUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ManageRequestsState {
    data class Loading(val isRefresh: Boolean) : ManageRequestsState()
    data class Error(val error: Throwable) : ManageRequestsState()
    data class Success(var requests: List<TestDeviceRequestUi>) : ManageRequestsState()
}

sealed class ManageRequestsUserEvent{
    data object Refresh: ManageRequestsUserEvent()
    data class ApproveRequest(val requestId: String) : ManageRequestsUserEvent()
    data class RejectRequest(val requestId: String) : ManageRequestsUserEvent()
}

@HiltViewModel
class ManageRequestsViewModel @Inject constructor(
    private val adminOperations: AdminUseCases
): ViewModel() {


    private val _state = MutableStateFlow<ManageRequestsState>(ManageRequestsState.Loading(false))
    val state = _state.asStateFlow()

    fun onEvent(event: ManageRequestsUserEvent){
        when(event){
            ManageRequestsUserEvent.Refresh -> {
                refreshRequests()
            }
            is ManageRequestsUserEvent.ApproveRequest -> {
                approveRequest(event.requestId)
            }
            is ManageRequestsUserEvent.RejectRequest -> {
                rejectRequest(event.requestId)
            }
        }
    }

    private fun refreshRequests(){
        viewModelScope.launch {
            try {
                _state.value = ManageRequestsState.Loading(true)
                val requests = adminOperations.loadRequests().getOrThrow().map { it.toTestDeviceRequestUi() }
                _state.value = ManageRequestsState.Success(
                    requests = requests
                )
            } catch (e: Exception) {
                _state.value = ManageRequestsState.Error(e)
            }
        }

    }

    private fun approveRequest(requestId: String) {
        viewModelScope.launch {
            try {
                adminOperations.approveRequestUseCase(requestId)
            } catch (e: Exception) {
                _state.value = ManageRequestsState.Error(e)
            }
        }
    }

    private fun rejectRequest(requestId: String) {
        viewModelScope.launch {
            try {
                adminOperations.rejectRequestUseCase(requestId)
            } catch (e: Exception) {
                _state.value = ManageRequestsState.Error(e)
            }
        }
    }

    fun loadRequests(){
        viewModelScope.launch {
            try {
                _state.value = ManageRequestsState.Loading(false)
                val requests = adminOperations.loadRequests().getOrThrow().map { it.toTestDeviceRequestUi() }
                _state.value = ManageRequestsState.Success(
                    requests = requests
                )
            } catch (e: Exception) {
                _state.value = ManageRequestsState.Error(e)
            }
        }

    }
}