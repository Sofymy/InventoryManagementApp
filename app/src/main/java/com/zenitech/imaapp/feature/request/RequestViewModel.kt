package com.zenitech.imaapp.feature.request

import androidx.lifecycle.ViewModel
import com.zenitech.imaapp.domain.usecases.qr_reader.QRReaderUseCases
import com.zenitech.imaapp.domain.usecases.request.RequestUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RequestViewModel @Inject constructor(
    private val requestUseCases: RequestUseCases
) : ViewModel() {

}