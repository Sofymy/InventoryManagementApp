package com.zenitech.imaapp.domain.usecases.request_test_device

import com.zenitech.imaapp.data.repository.RequestTestDeviceRepository
import kotlinx.coroutines.flow.first
import java.io.IOException

class LoadTestDeviceTypesUseCase(
    private val repository: RequestTestDeviceRepository
) {

    suspend operator fun invoke(): Result<List<String>> {
        return try {
            val devices = repository.getDeviceTypes().first()
            Result.success(devices)
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

}