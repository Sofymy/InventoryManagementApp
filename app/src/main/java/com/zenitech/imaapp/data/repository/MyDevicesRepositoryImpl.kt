package com.zenitech.imaapp.data.repository

import com.zenitech.imaapp.domain.model.DeviceAsset
import com.zenitech.imaapp.domain.model.DeviceStatus
import com.zenitech.imaapp.domain.model.MyDeviceResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MyDevicesRepositoryImpl : MyDevicesRepository {

    override fun getMyDevices(): Flow<List<MyDeviceResponse>> {
        return flow {
            val mockedMyDevices = listOf(
                MyDeviceResponse(
                    inventoryId = "INV001",
                    type = "MacBook Pro",
                    manufacturer = "Apple",
                    assetName = DeviceAsset.LAPTOP,
                    status = DeviceStatus.LEASED,
                    dateOfHandover = "2023-02-15"
                ),

                MyDeviceResponse(
                    inventoryId = "INV002",
                    type = "iPhone 13",
                    manufacturer = "Apple",
                    assetName = DeviceAsset.PHONE,
                    status = DeviceStatus.DELETED,
                    dateOfHandover = "2023-01-10"
                ),

                MyDeviceResponse(
                    inventoryId = "INV003",
                    type = "Galaxy Tab S7",
                    manufacturer = "Samsung",
                    assetName = DeviceAsset.TABLET,
                    status = DeviceStatus.MISSING,
                    dateOfHandover = "2023-03-05"
                ),

                MyDeviceResponse(
                    inventoryId = "INV004",
                    type = "HP Spectre x360",
                    manufacturer = "HP",
                    assetName = DeviceAsset.LAPTOP,
                    status = DeviceStatus.ON_REPAIR,
                    dateOfHandover = "2022-11-20"
                ),

                MyDeviceResponse(
                    inventoryId = "INV005",
                    type = "LG UltraFine 5K",
                    manufacturer = "LG",
                    assetName = DeviceAsset.MONITOR,
                    status = DeviceStatus.IN_STORAGE,
                    dateOfHandover = "2023-07-12"
                ),

                MyDeviceResponse(
                    inventoryId = "INV006",
                    type = "Google Pixel 6",
                    manufacturer = "Google",
                    assetName = DeviceAsset.PHONE,
                    status = DeviceStatus.DRAFT,
                    dateOfHandover = "2023-05-18"
                ),

                MyDeviceResponse(
                    inventoryId = "INV007",
                    type = "Lenovo ThinkPad X1 Carbon",
                    manufacturer = "Lenovo",
                    assetName = DeviceAsset.LAPTOP,
                    status = DeviceStatus.DELETED,
                    dateOfHandover = "2023-06-25"
                ),

                MyDeviceResponse(
                    inventoryId = "INV008",
                    type = "Microsoft Surface Pro 7",
                    manufacturer = "Microsoft",
                    assetName = DeviceAsset.TABLET,
                    status = DeviceStatus.DISCARDED,
                    dateOfHandover = "2023-04-14"
                ),

                MyDeviceResponse(
                    inventoryId = "INV009",
                    type = "Dell UltraSharp U2720Q",
                    manufacturer = "Dell",
                    assetName = DeviceAsset.MONITOR,
                    status = DeviceStatus.ARCHIVED,
                    dateOfHandover = "2023-08-01"
                )
            )
            val orderedMockedMyDevices = mockedMyDevices.sortedBy { it.assetName }
            emit(orderedMockedMyDevices)
        }
    }
}