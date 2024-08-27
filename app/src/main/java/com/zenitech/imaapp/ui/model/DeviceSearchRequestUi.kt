package com.zenitech.imaapp.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DeviceSearchRequestUi(
    val inventoryId: String = "",
    val type: String = "",
    val manufacturer: String = "",
    val serialNumber: String = "",
    val assetName: DeviceAssetUi = DeviceAssetUi.Laptop,
    val supplier: String = "",
    val invoiceNumber: String = "",
    val shipmentDateBegin: String = "",
    val shipmentDateEnd: String = "",
    val warrantyBegin: String = "",
    val warrantyEnd: String = "",
    val condition: DeviceConditionUi = DeviceConditionUi.NEW,
    val status: DeviceStatusUi = DeviceStatusUi.DRAFT,
    val leaseStartDate: String = "",
    val leaseEndDate: String = "",
    val userName: String = "",
    val site: String = "",
    val location: String = "",
    val note: String = "",
    val tags: List<String> = emptyList(),
    val isTestDevice: Boolean = false
): Parcelable {
    fun getDeviceDetails(): List<DeviceDetail> {
        return listOf(
            DeviceDetail(
                sectionTitle = "General Information",
                details = listOf(
                    "Inventory ID" to this.inventoryId,
                    "Serial number" to this.serialNumber,
                    "Invoice number" to this.invoiceNumber,
                )
            ),
            DeviceDetail(
                sectionTitle = "Location Information",
                details = listOf(
                    "Location" to this.location,
                    "Site" to this.site,
                    "Supplier" to this.supplier
                )
            ),
            DeviceDetail(
                sectionTitle = "Lease Information",
                details = listOf(
                    "Shipment date" to this.shipmentDateBegin,
                    "Lease start date" to this.leaseStartDate,
                    "Lease end date" to this.leaseEndDate,
                )
            )
        )
    }

}

data class DeviceDetail(val sectionTitle: String, val details: List<Pair<String, String>>)



