package com.zenitech.imaapp.domain.usecases.admin.devices

import com.zenitech.imaapp.data.repository.AdminRepository
import com.zenitech.imaapp.domain.model.HistoryResponse
import kotlinx.coroutines.flow.first
import java.io.IOException

class LoadAdminDeviceHistory(
    private val repository: AdminRepository
) {

    suspend operator fun invoke(inventoryId: String): Result<List<HistoryResponse>> {
        return try {
            val deviceHistory = repository.getDeviceHistory(inventoryId).first()
            Result.success(deviceHistory)
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

}