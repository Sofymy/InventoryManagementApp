package com.zenitech.imaapp.domain.usecases.my_devices

import com.zenitech.imaapp.data.repository.MyDevicesRepository

class MyDevicesUseCases(
    val repository: MyDevicesRepository,
    val loadMyDevices: LoadMyDevicesUseCase
)