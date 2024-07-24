package com.zenitech.imaapp.data.repository

import com.zenitech.imaapp.domain.model.DeviceAsset
import com.zenitech.imaapp.domain.model.DeviceSearchRequest
import com.zenitech.imaapp.domain.model.DeviceStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

class MyDevicesRepositoryImpl : MyDevicesRepository {

    // todo

    override fun getMyDevices(): Flow<List<DeviceSearchRequest>> {
        return flow {
            val mockedDevices = mutableListOf(
                DeviceSearchRequest(
                    inventoryId = "INV002",
                    asset = DeviceAsset.PHONE,
                    manufacturer = "Apple",
                    serialNumber = "SN234567",
                    itemNumber = "ITEM002",
                    supplier = "Apple Store",
                    invoiceNumber = "INV002B",
                    shipmentDate = ("2024.02.20"),
                    warranty = ("2027.02.20"),
                    status = DeviceStatus.MISSING,
                    leaseStartDate = ("2024.02.25"),
                    leaseEndDate = ("2025.02.25"),
                    userName = "UserB",
                    site = "Home",
                    location = "B2",
                    type = "iPhone 14 Pro"
                ),
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
                    status = DeviceStatus.DISCARDED,
                    leaseStartDate = ("2024.03.20"),
                    leaseEndDate = ("2025.03.20"),
                    userName = "UserC",
                    site = "Office",
                    location = "C3",
                    type = "Pavilion x360"
                ),
                DeviceSearchRequest(
                    inventoryId = "INV004",
                    asset = DeviceAsset.MONITOR,
                    manufacturer = "LG",
                    serialNumber = "SN456789",
                    itemNumber = "ITEM004",
                    supplier = "LG Electronics",
                    invoiceNumber = "INV004D",
                    shipmentDate = ("2024.04.05"),
                    warranty = ("2026.04.05"),
                    status = DeviceStatus.LEASED,
                    leaseStartDate = ("2024.04.10"),
                    leaseEndDate = ("2025.04.10"),
                    userName = "UserD",
                    site = "Office",
                    location = "D4",
                    type = "UltraWide 34WK95U-W"
                ),
                DeviceSearchRequest(
                    inventoryId = "INV005",
                    asset = DeviceAsset.PRINTER,
                    manufacturer = "Brother",
                    serialNumber = "SN567890",
                    itemNumber = "ITEM005",
                    supplier = "Brother Store",
                    invoiceNumber = "INV005E",
                    shipmentDate = ("2024.05.25"),
                    warranty = ("2027.05.25"),
                    status = DeviceStatus.LEASED,
                    leaseStartDate = ("2024.06.01"),
                    leaseEndDate = ("2025.06.01"),
                    userName = "UserE",
                    site = "Office",
                    location = "E5",
                    type = "HL-L2350DW"
                ),
                DeviceSearchRequest(
                    inventoryId = "INV006",
                    asset = DeviceAsset.SPEAKER,
                    manufacturer = "Bose",
                    serialNumber = "SN678901",
                    itemNumber = "ITEM006",
                    supplier = "Bose Store",
                    invoiceNumber = "INV006F",
                    shipmentDate = ("2024.06.10"),
                    warranty = ("2028.06.10"),
                    status = DeviceStatus.IN_STORAGE,
                    leaseStartDate = ("2024.06.15"),
                    leaseEndDate = ("2025.06.15"),
                    userName = "UserF",
                    site = "Home",
                    location = "F6",
                    type = "SoundLink Revolve+"
                ),
                DeviceSearchRequest(
                    inventoryId = "INV007",
                    asset = DeviceAsset.ROUTER,
                    manufacturer = "Netgear",
                    serialNumber = "SN789012",
                    itemNumber = "ITEM007",
                    supplier = "Netgear Store",
                    invoiceNumber = "INV007G",
                    shipmentDate = ("2024.07.20"),
                    warranty = ("2026.07.20"),
                    status = DeviceStatus.LEASED,
                    leaseStartDate = ("2024.07.25"),
                    leaseEndDate = ("2025.07.25"),
                    userName = "UserG",
                    site = "Office",
                    location = "G7",
                    type = "Nighthawk R7000"
                ),
                DeviceSearchRequest(
                    inventoryId = "INV008",
                    asset = DeviceAsset.TABLET,
                    manufacturer = "Samsung",
                    serialNumber = "SN890123",
                    itemNumber = "ITEM008",
                    supplier = "Samsung Store",
                    invoiceNumber = "INV008H",
                    shipmentDate = ("2024.08.15"),
                    warranty = ("2026.08.15"),
                    status = DeviceStatus.LEASED,
                    leaseStartDate = ("2024.08.20"),
                    leaseEndDate = ("2025.08.20"),
                    userName = "UserH",
                    site = "Home",
                    location = "H8",
                    type = "Galaxy Tab S7"
                )
            )
            val orderedMockedDevices = mockedDevices.sortedBy { it.asset.name }
            emit(orderedMockedDevices)
        }
    }
}