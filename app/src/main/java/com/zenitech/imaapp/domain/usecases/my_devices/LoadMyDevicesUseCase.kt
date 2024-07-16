package com.zenitech.imaapp.domain.usecases.my_devices

import com.zenitech.imaapp.data.repository.MyDevicesRepository
import com.zenitech.imaapp.domain.model.DeviceResponse
import kotlinx.coroutines.flow.first
import java.io.IOException

class LoadMyDevicesUseCase(
    private val repository: MyDevicesRepository
) {

    suspend operator fun invoke(): Result<List<DeviceResponse>> {
        return try {
            val devices = repository.getMyDevices().first()
            Result.success(devices)
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

}
