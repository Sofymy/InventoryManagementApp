package com.zenitech.imaapp.domain.usecases.admin.devices

import com.zenitech.imaapp.data.repository.AdminRepository
import com.zenitech.imaapp.domain.model.DeviceSearchRequest
import java.io.IOException

class AdminSaveModificationsUseCase(
    private val repository: AdminRepository
) {

    operator fun invoke(device: DeviceSearchRequest): Result<Unit> {
        return try {
            val deviceResponse = repository.saveModifications(device)
            Result.success(deviceResponse)
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

}