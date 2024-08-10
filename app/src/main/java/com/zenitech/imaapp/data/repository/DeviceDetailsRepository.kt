package com.zenitech.imaapp.data.repository

import com.zenitech.imaapp.domain.model.DeviceSearchRequest
import kotlinx.coroutines.flow.Flow

interface DeviceDetailsRepository {
    fun getDeviceDetails(inventoryId: String): Flow<DeviceSearchRequest>
}