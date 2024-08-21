package com.zenitech.imaapp.ui.model

import com.zenitech.imaapp.ui.utils.validation.EmptyFieldValidation
import com.zenitech.imaapp.ui.utils.validation.ValidationError

data class TestDeviceRequestUi(
    val requestId: String = "",

    @property:EmptyFieldValidation
    val asset: String = "",

    @property:EmptyFieldValidation
    val manufacturer: String = "",

    val startDate: String = "",

    val endDate: String = "",

    val note: String = "",

    val status: RequestStatusUi = RequestStatusUi.PENDING,

    val error: ValidationError? = null
)