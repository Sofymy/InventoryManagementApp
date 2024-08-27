package com.zenitech.imaapp.data.repository

import com.zenitech.imaapp.domain.model.TestDeviceRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RequestTestDeviceRepositoryImpl : RequestTestDeviceRepository {

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

    override fun saveTestDeviceRequest(testDeviceRequest: TestDeviceRequest) {
        // TODO("Not yet implemented")
    }
}