package com.zenitech.imaapp.domain.model

import com.zenitech.imaapp.ui.model.MyDeviceResponseUi

data class MyDeviceResponse(
    val assetName: DeviceAsset,
    val inventoryId: String,
    val manufacturer: String,
    val type: String,
    val dateOfHandover: String,
    val status: DeviceStatus
)

fun MyDeviceResponse.MyDeviceResponseUi(): MyDeviceResponseUi {
    return MyDeviceResponseUi(
        assetName = assetName.asDeviceAssetUi(),
        inventoryId = inventoryId,
        manufacturer = manufacturer,
        type = type,
        dateOfHandover = dateOfHandover,
        status = status.toDeviceStatusUi()
    )
}

fun MyDeviceResponseUi.toMyDeviceResponse(): MyDeviceResponse {
    return MyDeviceResponse(
        assetName = assetName.asDeviceAsset(),
        inventoryId = inventoryId,
        manufacturer = manufacturer,
        type = type,
        dateOfHandover = dateOfHandover,
        status = status.toDeviceStatus()
    )
}
