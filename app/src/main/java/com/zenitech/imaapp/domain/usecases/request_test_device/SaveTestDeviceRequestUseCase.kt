package com.zenitech.imaapp.domain.usecases.request_test_device

import android.content.ContentValues.TAG
import android.util.Log
import com.zenitech.imaapp.data.repository.RequestTestDeviceRepository
import com.zenitech.imaapp.domain.model.TestDeviceRequest
import kotlinx.coroutines.flow.first
import java.io.IOException

class SaveTestDeviceRequestUseCase (
    private val repository: RequestTestDeviceRepository
) {

    operator fun invoke(testDeviceRequest: TestDeviceRequest) {
        try {
            repository.saveTestDeviceRequest(testDeviceRequest)
        } catch (e: IOException) {
            Log.e(TAG, e.message.toString())
        }
    }
}

