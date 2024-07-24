package com.zenitech.imaapp.domain.usecases.device_details

import com.zenitech.imaapp.data.repository.DeviceDetailsRepository

class DeviceDetailsUseCases(
    val repository: DeviceDetailsRepository,
    val loadDeviceDetails: LoadDeviceDetailsUseCase
)