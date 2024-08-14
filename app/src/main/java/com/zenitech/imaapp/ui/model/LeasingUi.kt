package com.zenitech.imaapp.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LeasingUi(
    val leaseId: String = "",
    val inventoryId: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val userId: String = "",
    val userName: String = ""
): Parcelable