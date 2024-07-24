package com.zenitech.imaapp.data.repository

import com.zenitech.imaapp.domain.model.DeviceAsset
import com.zenitech.imaapp.domain.model.DeviceSearchRequest
import com.zenitech.imaapp.domain.model.DeviceStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeviceDetailsRepositoryImpl : DeviceDetailsRepository {

    // todo

    override fun getDeviceDetails(): Flow<DeviceSearchRequest> {
        return flow {
            val mockedDevice =
                DeviceSearchRequest(
                    inventoryId = "INV003",
                    asset = DeviceAsset.DESKTOP_COMPUTER,
                    manufacturer = "HP",
                    serialNumber = "SN345678",
                    itemNumber = "ITEM003",
                    supplier = "HP Store",
                    invoiceNumber = "INV003C",
                    shipmentDate = ("2024.03.15"),
                    warranty = ("2028.03.15"),
                    status = DeviceStatus.LEASED,
                    leaseStartDate = ("2024.03.20"),
                    leaseEndDate = ("2025.03.20"),
                    userName = "UserC",
                    site = "Office",
                    location = "C3",
                    type = "Pavilion x360"
                )
            emit(mockedDevice)
        }
    }
}