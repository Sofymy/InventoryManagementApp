package com.zenitech.imaapp.domain.usecases.device_details

import com.zenitech.imaapp.data.repository.DeviceDetailsRepository
import com.zenitech.imaapp.domain.model.DeviceSearchRequest
import com.zenitech.imaapp.domain.model.MyDeviceResponse
import kotlinx.coroutines.flow.first
import java.io.IOException

class LoadMyDeviceDetailsUseCase(
    private val repository: DeviceDetailsRepository
) {

    suspend operator fun invoke(inventoryId: String): Result<MyDeviceResponse> {
        return try {
            val devices = repository.getMyDeviceDetails(inventoryId).first()
            Result.success(devices)
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

}
