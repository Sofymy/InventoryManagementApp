package com.zenitech.imaapp.domain.usecases.admin.devices

import com.zenitech.imaapp.data.repository.AdminRepository
import kotlinx.coroutines.flow.first
import java.io.IOException

class LoadAdminAddDeviceTypesUseCase(
    private val repository: AdminRepository
) {

    suspend operator fun invoke(): Result<List<String>> {
        return try {
            val devicesTypes = repository.getDeviceTypes().first()
            Result.success(devicesTypes)
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

}