package com.zenitech.imaapp.domain.model

import com.zenitech.imaapp.ui.model.RequestStatusUi

enum class RequestStatus{
    PENDING,
    APPROVED,
    REJECTED
}

fun RequestStatus.toRequestStatusUi(): RequestStatusUi {
    return when (this) {
        RequestStatus.PENDING -> RequestStatusUi.PENDING
        RequestStatus.APPROVED -> RequestStatusUi.APPROVED
        RequestStatus.REJECTED -> RequestStatusUi.REJECTED
    }
}

fun RequestStatusUi.toRequestStatus(): RequestStatus {
    return when (this) {
        RequestStatusUi.PENDING -> RequestStatus.PENDING
        RequestStatusUi.APPROVED -> RequestStatus.APPROVED
        RequestStatusUi.REJECTED -> RequestStatus.REJECTED
    }
}