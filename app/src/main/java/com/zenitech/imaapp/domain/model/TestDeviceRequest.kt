package com.zenitech.imaapp.domain.model

import com.zenitech.imaapp.ui.model.TestDeviceRequestUi

data class TestDeviceRequest(
    val requestId: String,
    val manufacturer: String,
    val asset: String,
    val startDate: String,
    val endDate: String,
    val note: String
)

fun TestDeviceRequestUi.toTestDeviceRequest(): TestDeviceRequest {
    return TestDeviceRequest(
        requestId = "",
        manufacturer = manufacturer,
        asset = asset,
        startDate = startDate,
        endDate = endDate,
        note = note
    )
}

fun TestDeviceRequest.toTestDeviceRequestUi(): TestDeviceRequestUi {
    return TestDeviceRequestUi(
        manufacturer = manufacturer,
        asset = asset,
        startDate = startDate,
        endDate = endDate,
        note = note
    )
}