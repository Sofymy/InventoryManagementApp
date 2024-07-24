package com.zenitech.imaapp.domain.model

import com.squareup.moshi.Json

enum class DeviceStatus{
    LEASED,
    IN_STORAGE,
    DISCARDED,
    ON_REPAIR,
    MISSING
}