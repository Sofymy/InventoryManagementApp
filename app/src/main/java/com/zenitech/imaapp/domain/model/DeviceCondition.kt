package com.zenitech.imaapp.domain.model

import com.zenitech.imaapp.ui.model.DeviceConditionUi

enum class DeviceCondition {
    NEW,
    USED,
    DAMAGED
}

fun DeviceConditionUi.toDeviceCondition(): DeviceCondition {
    return when (this) {
        DeviceConditionUi.NEW -> DeviceCondition.NEW
        DeviceConditionUi.USED -> DeviceCondition.USED
        DeviceConditionUi.DAMAGED -> DeviceCondition.DAMAGED
    }
}

fun DeviceCondition.toDeviceConditionUi(): DeviceConditionUi {
    return when (this) {
        DeviceCondition.NEW -> DeviceConditionUi.NEW
        DeviceCondition.USED -> DeviceConditionUi.USED
        DeviceCondition.DAMAGED -> DeviceConditionUi.DAMAGED
    }
}