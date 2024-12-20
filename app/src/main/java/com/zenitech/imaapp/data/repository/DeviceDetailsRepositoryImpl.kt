package com.zenitech.imaapp.data.repository

import com.zenitech.imaapp.domain.model.DeviceAsset
import com.zenitech.imaapp.domain.model.DeviceCondition
import com.zenitech.imaapp.domain.model.DeviceSearchRequest
import com.zenitech.imaapp.domain.model.DeviceStatus
import com.zenitech.imaapp.domain.model.MyDeviceResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeviceDetailsRepositoryImpl : DeviceDetailsRepository {

    override fun getDeviceDetails(inventoryId: String): Flow<DeviceSearchRequest> {
        return flow {
            val mockedDevices = listOf(
                DeviceSearchRequest(
                    inventoryId = "INV001",
                    type = "Laptop",
                    manufacturer = "Dell",
                    serialNumber = "SN123456",
                    assetName = DeviceAsset.PHONE,
                    supplier = "TechSupply",
                    invoiceNumber = "INV0001",
                    shipmentDateBegin = "2023-01-01",
                    shipmentDateEnd = "2023-01-10",
                    warrantyBegin = "2023-01-11",
                    warrantyEnd = "2024-01-11",
                    condition = DeviceCondition.NEW,
                    status = DeviceStatus.DELETED,
                    leaseStartDate = "2023-02-01",
                    leaseEndDate = "2024-02-01",
                    userName = "John Doe",
                    site = "Headquarters",
                    location = "Office A",
                    note = "Urgent delivery",
                    tags = listOf("urgent", "high-value"),
                    isTestDevice = false
                ),
                DeviceSearchRequest(
                    inventoryId = "INV002",
                    type = "Monitor",
                    manufacturer = "Samsung",
                    serialNumber = "SN654321",
                    assetName = DeviceAsset.MONITOR,
                    supplier = "VisionTech",
                    invoiceNumber = "INV0002",
                    shipmentDateBegin = "2023-02-01",
                    shipmentDateEnd = "2023-02-15",
                    warrantyBegin = "2023-02-16",
                    warrantyEnd = "2024-02-16",
                    condition = DeviceCondition.USED,
                    status = DeviceStatus.MISSING,
                    leaseStartDate = "2023-03-01",
                    leaseEndDate = "2024-03-01",
                    userName = "Jane Smith",
                    site = "Branch Office",
                    location = "Office B",
                    note = null,
                    tags = listOf("monitor", "refurbished"),
                    isTestDevice = true
                ),
                DeviceSearchRequest(
                    inventoryId = "INV003",
                    type = "Printer",
                    manufacturer = "HP",
                    serialNumber = "SN789012",
                    assetName = DeviceAsset.PRINTER,
                    supplier = "PrintMasters",
                    invoiceNumber = "INV0003",
                    shipmentDateBegin = "2023-03-01",
                    shipmentDateEnd = "2023-03-10",
                    warrantyBegin = "2023-03-11",
                    warrantyEnd = "2024-03-11",
                    condition = DeviceCondition.USED,
                    status = DeviceStatus.ON_REPAIR,
                    leaseStartDate = "2023-04-01",
                    leaseEndDate = "2024-04-01",
                    userName = "Emily Johnson",
                    site = "Headquarters",
                    location = "Office C",
                    note = "Needs calibration",
                    tags = listOf("printer", "needs-calibration"),
                    isTestDevice = false
                ),
                DeviceSearchRequest(
                    inventoryId = "INV004",
                    type = "Router",
                    manufacturer = "Cisco",
                    serialNumber = "SN345678",
                    assetName = DeviceAsset.ROUTER,
                    supplier = "NetEquip",
                    invoiceNumber = "INV0004",
                    shipmentDateBegin = "2023-04-01",
                    shipmentDateEnd = "2023-04-05",
                    warrantyBegin = "2023-04-06",
                    warrantyEnd = "2024-04-06",
                    condition = DeviceCondition.NEW,
                    status = DeviceStatus.ARCHIVED,
                    leaseStartDate = "2023-05-01",
                    leaseEndDate = "2024-05-01",
                    userName = "Michael Brown",
                    site = "Data Center",
                    location = "Rack 5",
                    note = "Critical infrastructure",
                    tags = listOf("network", "critical"),
                    isTestDevice = false
                ),
                DeviceSearchRequest(
                    inventoryId = "INV005",
                    type = "Server",
                    manufacturer = "HP",
                    serialNumber = "SN987654",
                    assetName = DeviceAsset.SERVER,
                    supplier = "ServerWorld",
                    invoiceNumber = "INV0005",
                    shipmentDateBegin = "2023-05-01",
                    shipmentDateEnd = "2023-05-15",
                    warrantyBegin = "2023-05-16",
                    warrantyEnd = "2024-05-16",
                    condition = DeviceCondition.NEW,
                    status = DeviceStatus.IN_STORAGE,
                    leaseStartDate = "2023-06-01",
                    leaseEndDate = "2024-06-01",
                    userName = "Sarah Wilson",
                    site = "Headquarters",
                    location = "Server Room 1",
                    note = "High performance",
                    tags = listOf("server", "high-performance"),
                    isTestDevice = true
                ),
                DeviceSearchRequest(
                    inventoryId = "INV006",
                    type = "Tablet",
                    manufacturer = "Apple",
                    serialNumber = "SN135791",
                    assetName = DeviceAsset.TABLET,
                    supplier = "GadgetStore",
                    invoiceNumber = "INV0006",
                    shipmentDateBegin = "2023-06-01",
                    shipmentDateEnd = "2023-06-10",
                    warrantyBegin = "2023-06-11",
                    warrantyEnd = "2024-06-11",
                    condition = DeviceCondition.NEW,
                    status = DeviceStatus.DRAFT,
                    leaseStartDate = "2023-07-01",
                    leaseEndDate = "2024-07-01",
                    userName = "Lucas Martin",
                    site = "Branch Office",
                    location = "Office D",
                    note = "For testing",
                    tags = listOf("tablet", "testing"),
                    isTestDevice = false
                ),
                DeviceSearchRequest(
                    inventoryId = "INV007",
                    type = "Phone",
                    manufacturer = "Google",
                    serialNumber = "SN246810",
                    assetName = DeviceAsset.PHONE,
                    supplier = "MobileStore",
                    invoiceNumber = "INV0007",
                    shipmentDateBegin = "2023-07-01",
                    shipmentDateEnd = "2023-07-05",
                    warrantyBegin = "2023-07-06",
                    warrantyEnd = "2024-07-06",
                    condition = DeviceCondition.DAMAGED,
                    status = DeviceStatus.MISSING,
                    leaseStartDate = "2023-08-01",
                    leaseEndDate = "2024-08-01",
                    userName = "Olivia Brown",
                    site = "Headquarters",
                    location = "Office E",
                    note = "New model",
                    tags = listOf("phone", "new-model"),
                    isTestDevice = false
                ),
                DeviceSearchRequest(
                    inventoryId = "INV008",
                    type = "Camera",
                    manufacturer = "Canon",
                    serialNumber = "SN112233",
                    assetName = DeviceAsset.SECURITY_CAMERA,
                    supplier = "CameraShop",
                    invoiceNumber = "INV0008",
                    shipmentDateBegin = "2023-08-01",
                    shipmentDateEnd = "2023-08-10",
                    warrantyBegin = "2023-08-11",
                    warrantyEnd = "2024-08-11",
                    condition = DeviceCondition.USED,
                    status = DeviceStatus.DISCARDED,
                    leaseStartDate = "2023-09-01",
                    leaseEndDate = "2024-09-01",
                    userName = "Daniel Lee",
                    site = "Headquarters",
                    location = "Office F",
                    note = "For events",
                    tags = listOf("camera", "events"),
                    isTestDevice = true
                ),
                DeviceSearchRequest(
                    inventoryId = "INV009",
                    type = "Speaker",
                    manufacturer = "Bose",
                    serialNumber = "SN334455",
                    assetName = DeviceAsset.SPEAKER,
                    supplier = "AudioHub",
                    invoiceNumber = "INV0009",
                    shipmentDateBegin = "2023-09-01",
                    shipmentDateEnd = "2023-09-05",
                    warrantyBegin = "2023-09-06",
                    warrantyEnd = "2024-09-06",
                    condition = DeviceCondition.NEW,
                    status = DeviceStatus.LEASED,
                    leaseStartDate = "2023-10-01",
                    leaseEndDate = "2024-10-01",
                    userName = "Sophia Wilson",
                    site = "Branch Office",
                    location = "Office G",
                    note = null,
                    tags = listOf("speaker", "new"),
                    isTestDevice = false
                ),
                DeviceSearchRequest(
                    inventoryId = "INV010",
                    type = "Smartwatch",
                    manufacturer = "Fitbit",
                    serialNumber = "SN556677",
                    assetName = DeviceAsset.SMARTWATCH,
                    supplier = "WearableTech",
                    invoiceNumber = "INV0010",
                    shipmentDateBegin = "2023-10-01",
                    shipmentDateEnd = "2023-10-10",
                    warrantyBegin = "2023-10-11",
                    warrantyEnd = "2024-10-11",
                    condition = DeviceCondition.NEW,
                    status = DeviceStatus.LEASED,
                    leaseStartDate = "2023-11-01",
                    leaseEndDate = "2024-11-01",
                    userName = "Ava Johnson",
                    site = "Headquarters",
                    location = "Office H",
                    note = "Fitness tracker",
                    tags = listOf("smartwatch", "fitness"),
                    isTestDevice = true
                )
            )
            emit(mockedDevices.first {
                it.inventoryId == inventoryId
            })
        }
    }

    override fun getMyDeviceDetails(inventoryId: String): Flow<MyDeviceResponse> {
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

            emit(mockedMyDevices.first {
                it.inventoryId == inventoryId
            })
        }
    }
}