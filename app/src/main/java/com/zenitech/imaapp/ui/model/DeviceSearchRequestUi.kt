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
): Parcelable


