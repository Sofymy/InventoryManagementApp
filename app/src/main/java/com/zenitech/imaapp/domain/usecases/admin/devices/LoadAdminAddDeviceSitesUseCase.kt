package com.zenitech.imaapp.domain.usecases.admin.devices

import com.zenitech.imaapp.data.repository.AdminRepository
import kotlinx.coroutines.flow.first
import java.io.IOException

class LoadAdminAddDeviceSitesUseCase(
    private val repository: AdminRepository
) {

    suspend operator fun invoke(): Result<List<String>> {
        return try {
            val sites = repository.getDeviceSites().first()
            Result.success(sites)
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

}