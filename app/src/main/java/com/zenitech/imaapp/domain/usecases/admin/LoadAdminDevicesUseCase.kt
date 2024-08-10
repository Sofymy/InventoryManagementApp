package com.zenitech.imaapp.domain.usecases.admin

import com.zenitech.imaapp.data.repository.AdminRepository
import com.zenitech.imaapp.domain.model.DeviceSearchRequest
import kotlinx.coroutines.flow.first
import java.io.IOException

class LoadAdminDevicesUseCase(
    private val repository: AdminRepository
) {

    suspend operator fun invoke(): Result<List<DeviceSearchRequest>> {
        return try {
            val devices = repository.getAdminDevices().first()
            Result.success(devices)
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

}