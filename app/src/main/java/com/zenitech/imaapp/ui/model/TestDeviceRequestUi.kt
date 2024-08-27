package com.zenitech.imaapp.ui.model

import com.zenitech.imaapp.ui.utils.validation.EmptyFieldValidation
import com.zenitech.imaapp.ui.utils.validation.ValidationError
import java.time.LocalDate

data class TestDeviceRequestUi(
    val requestId: String = "",

    @property:EmptyFieldValidation
    val asset: String = "",

    @property:EmptyFieldValidation
    val manufacturer: String = "",

    val startDate: String = LocalDate.now().toString(),

    val endDate: String = LocalDate.now().plusWeeks(2).toString(),

    val note: String = "",

    val status: RequestStatusUi = RequestStatusUi.PENDING,

    val error: ValidationError? = null
)