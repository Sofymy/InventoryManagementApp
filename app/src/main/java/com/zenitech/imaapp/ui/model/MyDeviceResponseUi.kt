package com.zenitech.imaapp.ui.model

data class MyDeviceResponseUi(
    val assetName: DeviceAssetUi,
    val inventoryId: String,
    val manufacturer: String,
    val type: String,
    val dateOfHandover: String,
    val status: DeviceStatusUi
){
    fun getDeviceDetails(): List<DeviceDetail> {
        return listOf(
            DeviceDetail(
                sectionTitle = "General Information",
                details = listOf(
                    "Inventory ID" to this.inventoryId,
                    "Asset name" to this.assetName.label,
                )
            ),
            DeviceDetail(
                sectionTitle = "Technical Information",
                details = listOf(
                    "Manufacturer" to this.manufacturer,
                    "Type" to this.type,
                )
            ),
            DeviceDetail(
                sectionTitle = "Lease Information",
                details = listOf(
                    "Date of handover" to this.dateOfHandover
                )
            )
        )
    }
}

data class DeviceDetail(val sectionTitle: String, val details: List<Pair<String, String>>)
