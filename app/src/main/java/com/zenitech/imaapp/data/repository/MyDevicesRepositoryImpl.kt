package com.zenitech.imaapp.data.repository

import android.util.Log
import com.zenitech.imaapp.domain.model.DeviceResponse
import com.zenitech.imaapp.domain.model.DeviceType
import com.zenitech.imaapp.ui.model.DeviceResponseUi
import com.zenitech.imaapp.ui.model.DeviceTypeUi
import com.zenitech.imaapp.ui.model.asDeviceType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow

class MyDevicesRepositoryImpl : MyDevicesRepository {

    // todo

    override fun getMyDevices(): Flow<List<DeviceResponse>> {
        return flow {
            val mockedDevices = mutableListOf<DeviceResponse>()
            for (deviceType in DeviceTypeUi.entries) {
                mockedDevices.add(DeviceResponse(getRandomString(8),
                    deviceType.asDeviceType(),
                    getRandomManufacturer()
                ))
            }
            val orderedMockedDevices = mockedDevices.sortedBy { it.inventoryNumber }
            emit(orderedMockedDevices)
        }
    }
}

fun getRandomString(length: Int) : String {
    val allowedChars = ('A'..'Z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}

fun getRandomManufacturer() : String {
    val manufacturers = listOf("Apple", "LG", "Samsung")
    return manufacturers.random()
}