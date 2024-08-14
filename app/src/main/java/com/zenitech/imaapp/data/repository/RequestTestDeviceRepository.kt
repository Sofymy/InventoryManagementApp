package com.zenitech.imaapp.data.repository

import com.zenitech.imaapp.domain.model.TestDeviceRequest
import kotlinx.coroutines.flow.Flow

interface RequestTestDeviceRepository {
    fun getDeviceManufacturers(): Flow<List<String>>
    fun getDeviceTypes(): Flow<List<String>>
    fun saveTestDeviceRequest(testDeviceRequest: TestDeviceRequest)
}