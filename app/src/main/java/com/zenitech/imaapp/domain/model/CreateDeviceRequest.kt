package com.zenitech.imaapp.domain.model

import com.zenitech.imaapp.ui.model.CreateDeviceRequestUi

data class CreateDeviceRequest(
    val type: String,
    val manufacturer: String,
    val serialNumber: String,
    val assetName: String,
    val supplier: String,
    val invoiceNumber: String,
    val shipmentDate: String,
    val warranty: String,
    val condition: DeviceCondition,
    val status: DeviceStatus,
    val lease: Leasing?,
    val site: String,
    val location: String,
    val note: String
)

fun CreateDeviceRequestUi.toCreateDeviceRequest(): CreateDeviceRequest {
    return CreateDeviceRequest(
        type = type,
        manufacturer = manufacturer,
        serialNumber = serialNumber,
        assetName = assetName,
        supplier = supplier,
        invoiceNumber = invoiceNumber,
        shipmentDate = shipmentDate,
        warranty = warranty,
        condition = condition.toDeviceCondition(),
        status = status.toDeviceStatus(),
        lease = lease?.toLeasing(),
        site = site,
        location = location,
        note = note
    )
}

fun CreateDeviceRequest.toCreateDeviceRequestUi(): CreateDeviceRequestUi {
    return CreateDeviceRequestUi(
        type = type,
        manufacturer = manufacturer,
        serialNumber = serialNumber,
        assetName = assetName,
        supplier = supplier,
        invoiceNumber = invoiceNumber,
        shipmentDate = shipmentDate,
        warranty = warranty,
        condition = condition.toDeviceConditionUi(),
        status = status.toDeviceStatusUi(),
        lease = lease?.toLeasingUi(),
        site = site,
        location = location,
        note = note
    )
}