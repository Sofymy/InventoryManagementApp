package com.zenitech.imaapp.domain.usecases.request_test_device

import com.zenitech.imaapp.data.repository.RequestTestDeviceRepository
import kotlinx.coroutines.flow.first
import java.io.IOException

class LoadTestDeviceManufacturersUseCase(
    private val repository: RequestTestDeviceRepository
) {

    suspend operator fun invoke(): Result<List<String>> {
        return try {
            val devices = repository.getDeviceManufacturers().first()
            Result.success(devices)
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

}