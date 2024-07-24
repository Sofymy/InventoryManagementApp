package com.zenitech.imaapp.ui.model

import androidx.compose.ui.graphics.Color
import com.zenitech.imaapp.domain.model.DeviceStatus

enum class DeviceStatusUi(
    val color: Color
){
    LEASED(Color(67, 160, 71, 255)),
    IN_STORAGE(Color(30, 136, 229, 255)),
    DISCARDED(Color(255, 179, 0, 255)),
    ON_REPAIR(Color(0, 137, 123, 255)),
    MISSING(Color(229, 57, 53, 255))
}

fun DeviceStatus.toDeviceStatusUi(): DeviceStatusUi {
    return when (this) {
        DeviceStatus.LEASED -> DeviceStatusUi.LEASED
        DeviceStatus.IN_STORAGE -> DeviceStatusUi.IN_STORAGE
        DeviceStatus.DISCARDED -> DeviceStatusUi.DISCARDED
        DeviceStatus.ON_REPAIR -> DeviceStatusUi.ON_REPAIR
        DeviceStatus.MISSING -> DeviceStatusUi.MISSING
    }
}

fun DeviceStatusUi.toDeviceStatus(): DeviceStatus {
    return when (this) {
        DeviceStatusUi.LEASED -> DeviceStatus.LEASED
        DeviceStatusUi.IN_STORAGE -> DeviceStatus.IN_STORAGE
        DeviceStatusUi.DISCARDED -> DeviceStatus.DISCARDED
        DeviceStatusUi.ON_REPAIR -> DeviceStatus.ON_REPAIR
        DeviceStatusUi.MISSING -> DeviceStatus.MISSING
    }
}