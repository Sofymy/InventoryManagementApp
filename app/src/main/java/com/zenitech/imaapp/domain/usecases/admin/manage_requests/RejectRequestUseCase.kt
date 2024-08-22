package com.zenitech.imaapp.domain.usecases.admin.manage_requests

import com.zenitech.imaapp.data.repository.AdminRepository
import com.zenitech.imaapp.domain.model.DeviceAssignRequest
import java.io.IOException

class RejectRequestUseCase(
    private val repository: AdminRepository
) {

    suspend operator fun invoke(inventoryId: String): Result<Unit> {
        return try {
            val rejectRequestResponse = repository.rejectRequest(inventoryId)
            Result.success(rejectRequestResponse)
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

}