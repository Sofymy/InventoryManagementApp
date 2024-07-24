package com.zenitech.imaapp.domain.model

import com.zenitech.imaapp.ui.model.DeviceSearchRequestUi
import com.zenitech.imaapp.ui.model.asDeviceAsset
import com.zenitech.imaapp.ui.model.asDeviceAssetUi
import com.zenitech.imaapp.ui.model.toDeviceStatus
import com.zenitech.imaapp.ui.model.toDeviceStatusUi

data class DeviceSearchRequest(
    val inventoryId: String,
    val type: String,
    val asset: DeviceAsset,
    val manufacturer: String,
    val serialNumber: String,
    val itemNumber: String,
    val supplier: String,
    val invoiceNumber: String,
    val shipmentDate: String,
    val warranty: String,
    val status: DeviceStatus,
    val leaseStartDate: String,
    val leaseEndDate: String,
    val userName: String,
    val site: String,
    val location: String
)

fun DeviceSearchRequest.toDeviceSearchRequestUi(): DeviceSearchRequestUi {
    return DeviceSearchRequestUi(
        inventoryId = inventoryId,
        type = type,
        manufacturer = manufacturer,
        serialNumber = serialNumber,
        itemNumber = itemNumber,
        supplier = supplier,
        invoiceNumber = invoiceNumber,
        shipmentDate = shipmentDate,
        warranty = warranty,
        status = status.toDeviceStatusUi(),
        leaseStartDate = leaseStartDate,
        leaseEndDate = leaseEndDate,
        userName = userName,
        site = site,
        location = location,
        asset = asset.asDeviceAssetUi()
    )
}

fun DeviceSearchRequestUi.toDeviceSearchRequest(): DeviceSearchRequest {
    return DeviceSearchRequest(
        inventoryId = inventoryId,
        type = type,
        manufacturer = manufacturer,
        serialNumber = serialNumber,
        itemNumber = itemNumber,
        supplier = supplier,
        invoiceNumber = invoiceNumber,
        shipmentDate = shipmentDate,
        warranty = warranty,
        status = status.toDeviceStatus(),
        leaseStartDate = leaseStartDate,
        leaseEndDate = leaseEndDate,
        userName = userName,
        site = site,
        location = location,
        asset = asset.asDeviceAsset()
    )
}