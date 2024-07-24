package com.zenitech.imaapp.domain.usecases.device_details

import com.zenitech.imaapp.data.repository.DeviceDetailsRepository
import com.zenitech.imaapp.data.repository.MyDevicesRepository
import com.zenitech.imaapp.domain.model.DeviceSearchRequest
import kotlinx.coroutines.flow.first
import java.io.IOException

class LoadDeviceDetailsUseCase(
    private val repository: DeviceDetailsRepository
) {

    suspend operator fun invoke(): Result<DeviceSearchRequest> {
        return try {
            val devices = repository.getDeviceDetails().first()
            Result.success(devices)
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

}
