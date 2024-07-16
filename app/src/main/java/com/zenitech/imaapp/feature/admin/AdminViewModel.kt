package com.zenitech.imaapp.feature.admin

import androidx.lifecycle.ViewModel
import com.zenitech.imaapp.data.repository.AdminRepository
import com.zenitech.imaapp.data.repository.MyDevicesRepository
import com.zenitech.imaapp.domain.usecases.admin.AdminUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val adminOperations: AdminUseCases
): ViewModel() {

}