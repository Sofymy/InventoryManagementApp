package com.zenitech.imaapp.domain.usecases.admin.manage_requests

import com.zenitech.imaapp.data.repository.AdminRepository
import com.zenitech.imaapp.domain.model.TestDeviceRequest
import kotlinx.coroutines.flow.first
import java.io.IOException

class LoadRequestsUseCase(
    private val repository: AdminRepository
) {

    suspend operator fun invoke(): Result<List<TestDeviceRequest>> {
        return try {
            val requests = repository.getRequests().first()
            Result.success(requests)
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

}