package com.zenitech.imaapp.domain.model

import com.zenitech.imaapp.ui.model.DeviceResponseUi
import java.util.Date

data class DeviceResponse(
    val inventoryId: String,
    val asset: DeviceAsset,
    val manufacturer: String,
    val type: String,
    val serialNumber: String,
    val shipmentDate: Date,
    val status: DeviceStatus,
    val condition: DeviceCondition,
    val dateOfHandover: Date,
    val userName: String,
    val site: String,
    val location: String,
    val note: String,
    val supplier: String,
    val invoiceNumber: String,
    val warranty: Date
)

fun DeviceResponse.asDeviceResponseUi(): DeviceResponseUi = DeviceResponseUi(
    inventoryId = this.inventoryId,
    asset = this.asset.asDeviceAssetUi(),
    manufacturer = this.manufacturer,
    type = this.type,
    serialNumber = this.serialNumber,
    shipmentDate = this.shipmentDate,
    status = this.status.toDeviceStatusUi(),
    condition = this.condition.toDeviceConditionUi(),
    dateOfHandover = this.dateOfHandover,
    userName = this.userName,
    site = this.site,
    location = this.location,
    note = this.note,
    supplier = this.supplier,
    invoiceNumber = this.invoiceNumber,
    warranty = this.warranty
)

fun DeviceResponseUi.asDeviceResponse(): DeviceResponse = DeviceResponse(
    inventoryId = this.inventoryId,
    asset = this.asset.asDeviceAsset(),
    manufacturer = this.manufacturer,
    type = this.type,
    serialNumber = this.serialNumber,
    shipmentDate = this.shipmentDate,
    status = this.status.toDeviceStatus(),
    condition = this.condition.toDeviceCondition(),
    dateOfHandover = this.dateOfHandover,
    userName = this.userName,
    site = this.site,
    location = this.location,
    note = this.note,
    supplier = this.supplier,
    invoiceNumber = this.invoiceNumber,
    warranty = this.warranty
)