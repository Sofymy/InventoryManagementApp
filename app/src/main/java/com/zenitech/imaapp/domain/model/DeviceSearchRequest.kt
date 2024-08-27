package com.zenitech.imaapp.domain.model

import com.zenitech.imaapp.ui.model.DeviceSearchRequestUi

data class DeviceSearchRequest(
    val inventoryId: String,
    val type: String,
    val manufacturer: String,
    val serialNumber: String,
    val assetName: DeviceAsset,
    val supplier: String,
    val invoiceNumber: String,
    val shipmentDateBegin: String?,
    val shipmentDateEnd: String?,
    val warrantyBegin: String?,
    val warrantyEnd: String?,
    val condition: DeviceCondition,
    val status: DeviceStatus,
    val leaseStartDate: String?,
    val leaseEndDate: String?,
    val userName: String,
    val site: String,
    val location: String,
    val note: String?,
    val tags: List<String>,
    val isTestDevice: Boolean
)

fun DeviceSearchRequestUi.toDeviceSearchRequest(): DeviceSearchRequest {
    return DeviceSearchRequest(
        inventoryId = this.inventoryId,
        type = this.type,
        manufacturer = this.manufacturer,
        serialNumber = this.serialNumber,
        assetName = this.assetName.asDeviceAsset(),
        supplier = this.supplier,
        invoiceNumber = this.invoiceNumber,
        shipmentDateBegin = this.shipmentDateBegin.takeIf { it.isNotEmpty() },
        shipmentDateEnd = this.shipmentDateEnd.takeIf { it.isNotEmpty() },
        warrantyBegin = this.warrantyBegin.takeIf { it.isNotEmpty() },
        warrantyEnd = this.warrantyEnd.takeIf { it.isNotEmpty() },
        condition = this.condition.toDeviceCondition(),
        status = this.status.toDeviceStatus(),
        leaseStartDate = this.leaseStartDate.takeIf { it.isNotEmpty() },
        leaseEndDate = this.leaseEndDate.takeIf { it.isNotEmpty() },
        userName = this.userName,
        site = this.site,
        location = this.location,
        note = this.note.takeIf { it.isNotEmpty() },
        tags = this.tags,
        isTestDevice = this.isTestDevice
    )
}

fun DeviceSearchRequest.toDeviceSearchRequestUi(): DeviceSearchRequestUi {
    return DeviceSearchRequestUi(
        inventoryId = this.inventoryId,
        type = this.type,
        manufacturer = this.manufacturer,
        serialNumber = this.serialNumber,
        assetName = this.assetName.asDeviceAssetUi(),
        supplier = this.supplier,
        invoiceNumber = this.invoiceNumber,
        shipmentDateBegin = this.shipmentDateBegin ?: "",
        shipmentDateEnd = this.shipmentDateEnd ?: "",
        warrantyBegin = this.warrantyBegin ?: "",
        warrantyEnd = this.warrantyEnd ?: "",
        condition = this.condition.toDeviceConditionUi(),
        status = this.status.toDeviceStatusUi(),
        leaseStartDate = this.leaseStartDate ?: "",
        leaseEndDate = this.leaseEndDate ?: "",
        userName = this.userName,
        site = this.site,
        location = this.location,
        note = this.note ?: "",
        tags = this.tags,
        isTestDevice = this.isTestDevice
    )
}