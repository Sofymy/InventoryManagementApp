package com.zenitech.imaapp.ui.model

import java.util.Date

data class DeviceResponseUi(
    val inventoryId: String = "",
    val asset: DeviceAssetUi = DeviceAssetUi.Laptop,
    val manufacturer: String = "",
    val type: String = "",
    val serialNumber: String = "",
    val shipmentDate: Date = Date(),
    val status: DeviceStatusUi = DeviceStatusUi.DRAFT,
    val condition: DeviceConditionUi = DeviceConditionUi.NEW,
    val dateOfHandover: Date = Date(),
    val userName: String = "",
    val site: String = "",
    val location: String = "",
    val note: String = "",
    val supplier: String = "",
    val invoiceNumber: String = "",
    val warranty: Date = Date()
)

