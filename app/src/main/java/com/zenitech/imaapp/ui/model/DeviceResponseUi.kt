package com.zenitech.imaapp.ui.model

import java.util.Date

data class DeviceResponseUi(
    val inventoryId: String,
    val asset: DeviceAssetUi,
    val manufacturer: String,
    val type: String,
    val serialNumber: String,
    val shipmentDate: Date,
    val status: String,
    val condition: String,
    val dateOfHandover: Date,
    val userName: String,
    val site: String,
    val location: String,
    val note: String,
    val supplier: String,
    val invoiceNumber: String,
    val warranty: Date,
)

