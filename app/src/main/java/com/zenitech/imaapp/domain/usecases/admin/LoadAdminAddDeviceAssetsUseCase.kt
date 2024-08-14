package com.zenitech.imaapp.domain.usecases.admin

import com.zenitech.imaapp.data.repository.AdminRepository
import kotlinx.coroutines.flow.first
import java.io.IOException

class LoadAdminAddDeviceAssetsUseCase(
    private val repository: AdminRepository
) {

    suspend operator fun invoke(): Result<List<String>> {
        return try {
            val assets = repository.getDeviceAssets().first()
            Result.success(assets)
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

}