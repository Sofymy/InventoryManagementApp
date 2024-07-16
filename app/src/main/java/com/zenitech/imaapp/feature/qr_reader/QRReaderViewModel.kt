package com.zenitech.imaapp.feature.qr_reader

import androidx.lifecycle.ViewModel
import com.zenitech.imaapp.domain.usecases.qr_reader.QRReaderUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QRReaderViewModel @Inject constructor(
    private val qRReaderOperations: QRReaderUseCases
) : ViewModel() {

}