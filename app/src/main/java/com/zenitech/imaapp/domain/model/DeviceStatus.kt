package com.zenitech.imaapp.domain.model

import com.zenitech.imaapp.ui.model.DeviceStatusUi

enum class DeviceStatus{
    LEASED,
    IN_STORAGE,
    DISCARDED,
    ON_REPAIR,
    MISSING,
    DELETED,
    ARCHIVED,
    DRAFT
}

fun DeviceStatusUi.toDeviceStatus(): DeviceStatus {
    return when (this) {
        DeviceStatusUi.LEASED -> DeviceStatus.LEASED
        DeviceStatusUi.IN_STORAGE -> DeviceStatus.IN_STORAGE
        DeviceStatusUi.DISCARDED -> DeviceStatus.DISCARDED
        DeviceStatusUi.ON_REPAIR -> DeviceStatus.ON_REPAIR
        DeviceStatusUi.MISSING -> DeviceStatus.MISSING
        DeviceStatusUi.DELETED -> DeviceStatus.DELETED
        DeviceStatusUi.ARCHIVED -> DeviceStatus.ARCHIVED
        DeviceStatusUi.DRAFT -> DeviceStatus.DRAFT
    }
}

fun DeviceStatus.toDeviceStatusUi(): DeviceStatusUi {
    return when (this) {
        DeviceStatus.LEASED -> DeviceStatusUi.LEASED
        DeviceStatus.IN_STORAGE -> DeviceStatusUi.IN_STORAGE
        DeviceStatus.DISCARDED -> DeviceStatusUi.DISCARDED
        DeviceStatus.ON_REPAIR -> DeviceStatusUi.ON_REPAIR
        DeviceStatus.MISSING -> DeviceStatusUi.MISSING
        DeviceStatus.DELETED -> DeviceStatusUi.DELETED
        DeviceStatus.ARCHIVED -> DeviceStatusUi.ARCHIVED
        DeviceStatus.DRAFT -> DeviceStatusUi.DRAFT
    }
}
