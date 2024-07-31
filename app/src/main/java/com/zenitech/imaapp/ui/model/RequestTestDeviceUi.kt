package com.zenitech.imaapp.ui.model

import com.zenitech.imaapp.ui.utils.validation.EmptyFieldValidation
import com.zenitech.imaapp.ui.utils.validation.ValidationError

data class RequestTestDeviceUi(
    @property:EmptyFieldValidation()
    val type: String = "",

    @property:EmptyFieldValidation()
    val manufacturer: String = "",

    val requestDate: String = "",

    val returnDate: String = "",

    val additionalRequests: String? = null,

    val error: ValidationError? = null
)
