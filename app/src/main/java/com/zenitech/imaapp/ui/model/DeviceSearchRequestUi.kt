package com.zenitech.imaapp.ui.model

data class DeviceSearchRequestUi(
    val inventoryId: String,
    val type: String,
    val asset: DeviceAssetUi,
    val manufacturer: String,
    val serialNumber: String,
    val itemNumber: String,
    val supplier: String,
    val invoiceNumber: String,
    val shipmentDate: String,
    val warranty: String,
    val status: DeviceStatusUi,
    val leaseStartDate: String,
    val leaseEndDate: String,
    val userName: String,
    val site: String,
    val location: String
){
    fun getDeviceDetails(): List<DeviceDetail> {
        return listOf(
            DeviceDetail(
                sectionTitle = "Basic Information",
                details = listOf(
                    "Inventory ID" to this.inventoryId,
                    "Item number" to this.itemNumber,
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
                    "Shipment date" to this.shipmentDate,
                    "Lease start date" to this.leaseStartDate,
                    "Lease end date" to this.leaseEndDate,
                    "Warranty" to this.warranty
                )
            )
        )
    }
}

data class DeviceDetail(val sectionTitle: String, val details: List<Pair<String, String>>)

