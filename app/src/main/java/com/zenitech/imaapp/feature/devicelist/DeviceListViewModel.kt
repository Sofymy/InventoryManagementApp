package com.zenitech.imaapp.feature.devicelist

import androidx.lifecycle.ViewModel
import com.zenitech.imaapp.data.repository.DeviceListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeviceListViewModel @Inject constructor(
    private val repository: DeviceListRepository
) : ViewModel() {

}