package com.zenitech.imaapp.data.repository

import com.zenitech.imaapp.domain.model.DeviceSearchRequest
import kotlinx.coroutines.flow.Flow
import org.openapitools.client.models.MyDeviceResponse

interface MyDevicesRepository {
    fun getMyDevices(): Flow<List<DeviceSearchRequest>>
}