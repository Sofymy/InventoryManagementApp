package com.zenitech.imaapp.domain.usecases.admin.manage_requests

import com.zenitech.imaapp.data.repository.AdminRepository
import java.io.IOException

class ApproveRequestUseCase(
    private val repository: AdminRepository
) {

    operator fun invoke(inventoryId: String): Result<Unit> {
        return try {
            val approveRequestResponse = repository.assignDevice(inventoryId)
            Result.success(approveRequestResponse)
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

}