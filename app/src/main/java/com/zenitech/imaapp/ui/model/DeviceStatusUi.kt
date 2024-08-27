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
    MISSING(Color(229, 57, 53, 255)),
    DRAFT(Color(94, 53, 177, 255)),
    ARCHIVED(Color(94, 94, 94, 255)),
    DELETED(Color(244, 81, 30, 255))
}