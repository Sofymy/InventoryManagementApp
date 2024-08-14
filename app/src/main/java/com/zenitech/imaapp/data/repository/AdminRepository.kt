package com.zenitech.imaapp.data.repository

import com.zenitech.imaapp.domain.model.CreateDeviceRequest
import com.zenitech.imaapp.domain.model.DeviceResponse
import com.zenitech.imaapp.domain.model.DeviceSearchRequest
import kotlinx.coroutines.flow.Flow

interface AdminRepository {
    fun getAdminDevices(): Flow<List<DeviceSearchRequest>>
    fun getDeviceTypes(): Flow<List<String>>
    fun getDeviceManufacturers(): Flow<List<String>>
    fun getDeviceAssets(): Flow<List<String>>
    fun getDeviceSites(): Flow<List<String>>
    fun createDevice(createDeviceRequest: CreateDeviceRequest): Flow<DeviceResponse>
}