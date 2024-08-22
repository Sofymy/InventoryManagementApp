package com.zenitech.imaapp.domain.usecases.admin.devices

import com.zenitech.imaapp.data.repository.AdminRepository
import com.zenitech.imaapp.domain.model.CreateDeviceRequest
import com.zenitech.imaapp.domain.model.DeviceResponse
import kotlinx.coroutines.flow.first
import java.io.IOException

class AdminCreateDeviceUseCase(
    private val repository: AdminRepository
) {

    suspend operator fun invoke(createDeviceRequest: CreateDeviceRequest): Result<DeviceResponse> {
        return try {
            val deviceResponse = repository.createDevice(createDeviceRequest).first()
            Result.success(deviceResponse)
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

}