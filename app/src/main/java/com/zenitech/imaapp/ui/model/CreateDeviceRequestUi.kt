package com.zenitech.imaapp.ui.model

import android.os.Parcelable
import com.zenitech.imaapp.ui.utils.validation.EmptyFieldValidation
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class CreateDeviceRequestUi(

    @property:EmptyFieldValidation
    val type: String = "",

    @property:EmptyFieldValidation
    val manufacturer: String = "",

    @property:EmptyFieldValidation
    val serialNumber: String = "",

    @property:EmptyFieldValidation
    val assetName: String = "",

    @property:EmptyFieldValidation
    val supplier: String = "",

    @property:EmptyFieldValidation
    val invoiceNumber: String = "",

    @property:EmptyFieldValidation
    val shipmentDate: String = LocalDate.now().toString(),

    val warranty: String = LocalDate.now().toString(),

    val condition: DeviceConditionUi = DeviceConditionUi.NEW,

    val status: DeviceStatusUi = DeviceStatusUi.DRAFT,

    val lease: LeasingUi? = null,

    @property:EmptyFieldValidation
    val site: String = "",

    val location: String = "Budapest",

    val note: String = ""
): Parcelable
