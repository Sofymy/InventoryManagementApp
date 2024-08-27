package com.zenitech.imaapp.ui.model

data class HistoryResponseUi (
    val timestamp: String = "",
    val action: String = "",
    val modifierEmail: String = "",
    val description: String? = null,
    val actorEmail: String? = null
)