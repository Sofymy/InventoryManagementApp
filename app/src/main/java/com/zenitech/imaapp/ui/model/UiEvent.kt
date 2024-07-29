package com.zenitech.imaapp.ui.model

import com.zenitech.imaapp.ui.utils.validation.ValidationError

sealed class UiEvent {
    data object Success: UiEvent()
    data class Failure(val error: List<ValidationError?>): UiEvent()
}