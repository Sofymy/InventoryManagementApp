package com.zenitech.imaapp.domain.model

import com.zenitech.imaapp.ui.model.HistoryResponseUi

data class HistoryResponse (
    val timestamp: String,
    val action: String,
    val modifierEmail: String,
    val description: String? = null,
    val actorEmail: String? = null
)

fun HistoryResponse.toHistoryResponseUi(): HistoryResponseUi {
    return HistoryResponseUi(
        timestamp = this.timestamp,
        action = this.action,
        modifierEmail = this.modifierEmail,
        description = this.description,
        actorEmail = this.actorEmail
    )
}

fun HistoryResponseUi.toHistoryResponse(): HistoryResponse {
    return HistoryResponse(
        timestamp = this.timestamp,
        action = this.action,
        modifierEmail = this.modifierEmail,
        description = this.description,
        actorEmail = this.actorEmail
    )
}