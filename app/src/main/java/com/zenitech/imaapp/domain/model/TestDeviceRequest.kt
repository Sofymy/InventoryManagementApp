package com.zenitech.imaapp.domain.model

import com.zenitech.imaapp.ui.model.TestDeviceRequestUi

data class TestDeviceRequest(
    val requestId: String,
    val manufacturer: String,
    val asset: String,
    val startDate: String,
    val endDate: String,
    val note: String,
    val status: RequestStatus
)

fun TestDeviceRequestUi.toTestDeviceRequest(): TestDeviceRequest {
    return TestDeviceRequest(
        requestId = "",
        manufacturer = manufacturer,
        asset = asset,
        startDate = startDate,
        endDate = endDate,
        note = note,
        status = status.toRequestStatus()
    )
}

fun TestDeviceRequest.toTestDeviceRequestUi(): TestDeviceRequestUi {
    return TestDeviceRequestUi(
        requestId = requestId,
        manufacturer = manufacturer,
        asset = asset,
        startDate = startDate,
        endDate = endDate,
        note = note,
        status = status.toRequestStatusUi()
    )
}