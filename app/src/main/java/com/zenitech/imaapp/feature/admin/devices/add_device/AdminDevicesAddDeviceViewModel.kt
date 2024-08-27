package com.zenitech.imaapp.feature.admin.devices.add_device

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenitech.imaapp.domain.model.toCreateDeviceRequest
import com.zenitech.imaapp.domain.usecases.admin.AdminUseCases
import com.zenitech.imaapp.ui.model.CreateDeviceRequestUi
import com.zenitech.imaapp.ui.model.DeviceConditionUi
import com.zenitech.imaapp.ui.model.DeviceStatusUi
import com.zenitech.imaapp.ui.model.LeasingUi
import com.zenitech.imaapp.ui.utils.validation.ValidateState
import com.zenitech.imaapp.ui.utils.validation.ValidationError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AdminDevicesAddDeviceUserEvent {
    data class ChangeDeviceAsset(val assetName: String) : AdminDevicesAddDeviceUserEvent()
    data class ChangeDeviceType(val type: String) : AdminDevicesAddDeviceUserEvent()
    data class ChangeDeviceManufacturer(val manufacturer: String) : AdminDevicesAddDeviceUserEvent()
    data class ChangeInvoiceNumber(val invoiceNumber: String) : AdminDevicesAddDeviceUserEvent()
    data class ChangeSerialNumber(val serialNumber: String) : AdminDevicesAddDeviceUserEvent()
    data class ChangeShipmentDate(val shipmentDate: String) : AdminDevicesAddDeviceUserEvent()
    data class ChangeWarranty(val warranty: String) : AdminDevicesAddDeviceUserEvent()
    data class ChangeStatus(val status: DeviceStatusUi) : AdminDevicesAddDeviceUserEvent()
    data class ChangeCondition(val condition: DeviceConditionUi) : AdminDevicesAddDeviceUserEvent()
    data class ChangeLease(val lease: LeasingUi) : AdminDevicesAddDeviceUserEvent()
    data class ChangeSite(val site: String) : AdminDevicesAddDeviceUserEvent()
    data class ChangeSupplier(val supplier: String) : AdminDevicesAddDeviceUserEvent()
    data class ChangeNote(val note: String) : AdminDevicesAddDeviceUserEvent()
    data object SaveRequest : AdminDevicesAddDeviceUserEvent()
}

sealed class AdminDevicesAddDeviceState {
    data object Loading : AdminDevicesAddDeviceState()
    data object Success : AdminDevicesAddDeviceState()
    data class Failure(val error: List<ValidationError?>) : AdminDevicesAddDeviceState()

}

@HiltViewModel
class AdminDevicesAddDeviceViewModel @Inject constructor(
    private val adminOperations: AdminUseCases
) : ViewModel() {

    private val _state = MutableStateFlow<AdminDevicesAddDeviceState>(AdminDevicesAddDeviceState.Loading)
    val state = _state.asStateFlow()

    private var uiState by mutableStateOf(CreateDeviceRequestUi())

    fun onEvent(event: AdminDevicesAddDeviceUserEvent) {
        when (event) {
            is AdminDevicesAddDeviceUserEvent.ChangeDeviceAsset -> {
                uiState = uiState.copy(assetName = event.assetName)
            }

            is AdminDevicesAddDeviceUserEvent.ChangeDeviceType -> {
                uiState = uiState.copy(type = event.type)
            }

            is AdminDevicesAddDeviceUserEvent.ChangeDeviceManufacturer -> {
                uiState = uiState.copy(manufacturer = event.manufacturer)
            }

            is AdminDevicesAddDeviceUserEvent.ChangeInvoiceNumber -> {
                uiState = uiState.copy(invoiceNumber = event.invoiceNumber)
            }

            is AdminDevicesAddDeviceUserEvent.ChangeSerialNumber -> {
                uiState = uiState.copy(serialNumber = event.serialNumber)
            }

            is AdminDevicesAddDeviceUserEvent.ChangeShipmentDate -> {
                uiState = uiState.copy(shipmentDate = event.shipmentDate)
            }

            is AdminDevicesAddDeviceUserEvent.ChangeWarranty -> {
                uiState = uiState.copy(warranty = event.warranty)
            }

            is AdminDevicesAddDeviceUserEvent.ChangeStatus -> {
                uiState = uiState.copy(status = event.status)
            }

            is AdminDevicesAddDeviceUserEvent.ChangeCondition -> {
                uiState = uiState.copy(condition = event.condition)
            }

            is AdminDevicesAddDeviceUserEvent.ChangeLease -> {
                uiState = uiState.copy(lease = event.lease)
            }

            is AdminDevicesAddDeviceUserEvent.ChangeSite -> {
                uiState = uiState.copy(site = event.site)
            }

            is AdminDevicesAddDeviceUserEvent.ChangeSupplier -> {
                uiState = uiState.copy(supplier = event.supplier)
            }

            is AdminDevicesAddDeviceUserEvent.ChangeNote -> {
                uiState = uiState.copy(note = event.note)
            }

            AdminDevicesAddDeviceUserEvent.SaveRequest -> {
                onSave()
            }
        }
    }

    private fun onSave() {
        val stateValidator = ValidateState(CreateDeviceRequestUi::class)
        val errors = stateValidator.validate(uiState)

        viewModelScope.launch {
            if (errors.isEmpty()) {
                try {
                    adminOperations.createDevice(uiState.toCreateDeviceRequest())
                    _state.value = AdminDevicesAddDeviceState.Success
                    Log.d(LOG_TAG, "Save successful")
                } catch (e: Exception) {
                    Log.e(LOG_TAG, "Save failed", e)
                    _state.value = AdminDevicesAddDeviceState.Failure(listOf(ValidationError(message = e.message.toString())))
                }
            } else {
                _state.value = AdminDevicesAddDeviceState.Failure(errors)
            }
        }


    }



    companion object {
        private const val LOG_TAG = "RequestTestDeviceVM"
    }
}
