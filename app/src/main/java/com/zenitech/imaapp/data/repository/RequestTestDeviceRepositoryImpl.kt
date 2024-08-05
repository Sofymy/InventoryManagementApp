package com.zenitech.imaapp.data.repository

import com.zenitech.imaapp.domain.model.DeviceAsset
import com.zenitech.imaapp.domain.model.DeviceSearchRequest
import com.zenitech.imaapp.domain.model.DeviceStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RequestTestDeviceRepositoryImpl : RequestTestDeviceRepository {

    // todo
    override fun getDeviceManufacturers(): Flow<List<String>> {
        return flow {
            val mockedDeviceManufacturers = mutableListOf(
                "Apple",
                "Samsung",
                "Huawei",
                "Xiaomi",
                "Oppo",
                "Vivo",
                "Sony",
                "LG",
                "Lenovo",
                "ASUS",
                "Microsoft",
                "Google",
                "Nokia",
                "OnePlus",
                "Motorola",
                "Dell",
                "HP",
                "Acer",
                "Amazon",
                "Panasonic"
            )
            emit(mockedDeviceManufacturers)
        }
    }

    override fun getDeviceTypes(): Flow<List<String>> {
        return flow {
            val mockedDeviceTypes = mutableListOf(
                "Smartphone",
                "Tablet",
                "Laptop",
                "Desktop Computer",
                "Smartwatch",
                "Fitness Tracker",
                "E-Reader",
                "Gaming Console",
                "Smart TV",
                "VR Headset",
                "Smart Speaker",
                "Wireless Earbuds",
                "Digital Camera",
                "Home Security Camera",
                "Streaming Media Player",
                "Portable Media Player",
                "Smart Home Hub",
                "Smart Thermostat",
                "Drone",
                "Bluetooth Headphones"
            )
            emit(mockedDeviceTypes)
        }
    }
}