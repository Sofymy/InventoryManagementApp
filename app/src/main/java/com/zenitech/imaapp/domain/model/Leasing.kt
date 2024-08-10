package com.zenitech.imaapp.domain.model

import com.zenitech.imaapp.ui.model.LeasingUi

data class Leasing(
    val leaseId: String,
    val inventoryId: String,
    val startDate: String,
    val endDate: String,
    val userId: String,
    val userName: String,
)

fun LeasingUi.toLeasing(): Leasing {
    return Leasing(
        leaseId = leaseId,
        inventoryId = inventoryId,
        startDate = startDate,
        endDate = endDate,
        userId = userId,
        userName = userName
    )
}

fun Leasing.toLeasingUi(): LeasingUi {
    return LeasingUi(
        leaseId = leaseId,
        inventoryId = inventoryId,
        startDate = startDate,
        endDate = endDate,
        userId = userId,
        userName = userName
    )
}
