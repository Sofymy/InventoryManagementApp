package com.zenitech.imaapp.domain.usecases.admin.devices

import com.zenitech.imaapp.data.repository.AdminRepository
import com.zenitech.imaapp.domain.model.CreateDeviceRequest
import com.zenitech.imaapp.domain.model.DeviceResponse
import com.zenitech.imaapp.domain.model.DeviceSearchRequest
import com.zenitech.imaapp.ui.model.DeviceSearchRequestUi
import kotlinx.coroutines.flow.first
import java.io.IOException

class AdminDeleteDeviceUseCase(
    private val repository: AdminRepository
) {

    operator fun invoke(device: DeviceSearchRequest): Result<Unit> {
        return try {
            val deviceResponse = repository.deleteDevice(device)
            Result.success(deviceResponse)
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

}