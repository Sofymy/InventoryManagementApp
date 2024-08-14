package com.zenitech.imaapp.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreateDeviceRequestUi(
    val type: String = "",
    val manufacturer: String = "",
    val serialNumber: String = "",
    val assetName: String = "",
    val supplier: String = "",
    val invoiceNumber: String = "",
    val shipmentDate: String = "",
    val warranty: String = "",
    val condition: DeviceConditionUi = DeviceConditionUi.NEW,
    val status: DeviceStatusUi = DeviceStatusUi.DRAFT,
    val lease: LeasingUi? = null,
    val site: String = "",
    val location: String = "",
    val note: String = ""
): Parcelable
