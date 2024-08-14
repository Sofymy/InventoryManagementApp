package com.zenitech.imaapp.domain.usecases.admin

import com.zenitech.imaapp.data.repository.AdminRepository
import kotlinx.coroutines.flow.first
import java.io.IOException

class LoadAdminAddDeviceManufacturersUseCase(
    private val repository: AdminRepository
) {

        suspend operator fun invoke(): Result<List<String>> {
            return try {
                val manufacturers = repository.getDeviceManufacturers().first()
                Result.success(manufacturers)
            } catch (e: IOException) {
                Result.failure(e)
            }
        }

    }