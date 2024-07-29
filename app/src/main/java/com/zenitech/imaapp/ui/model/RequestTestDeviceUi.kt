package com.zenitech.imaapp.ui.model

import com.zenitech.imaapp.ui.utils.validation.DeviceManufacturerValidation
import com.zenitech.imaapp.ui.utils.validation.DeviceTypeValidation
import com.zenitech.imaapp.ui.utils.validation.ValidationError

data class RequestTestDeviceUi(
    @property:DeviceTypeValidation()
    val type: String = "",

    @property:DeviceManufacturerValidation()
    val manufacturer: String = "",

    val requestDate: String = "",

    val returnDate: String = "",

    val additionalRequests: String? = null,

    val error: ValidationError? = null
)
