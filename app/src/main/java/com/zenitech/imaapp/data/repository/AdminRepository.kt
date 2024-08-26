package com.zenitech.imaapp.data.repository

import com.zenitech.imaapp.domain.model.CreateDeviceRequest
import com.zenitech.imaapp.domain.model.DeviceResponse
import com.zenitech.imaapp.domain.model.DeviceSearchRequest
import com.zenitech.imaapp.domain.model.HistoryResponse
import com.zenitech.imaapp.domain.model.TestDeviceRequest
import kotlinx.coroutines.flow.Flow

interface AdminRepository {
    fun getAdminDevices(): Flow<List<DeviceSearchRequest>>
    fun getDeviceHistory(inventoryId: String): Flow<List<HistoryResponse>>
    fun getDeviceTypes(): Flow<List<String>>
    fun getDeviceManufacturers(): Flow<List<String>>
    fun getDeviceAssets(): Flow<List<String>>
    fun getDeviceSites(): Flow<List<String>>
    fun getRequests(): Flow<List<TestDeviceRequest>>
    fun createDevice(createDeviceRequest: CreateDeviceRequest): Flow<DeviceResponse>
    fun assignDevice(assignDeviceRequest: String)
    fun rejectRequest(assignDeviceRequest: String)
    fun deleteDevice(device: DeviceSearchRequest)
    fun saveModifications(device: DeviceSearchRequest)
}