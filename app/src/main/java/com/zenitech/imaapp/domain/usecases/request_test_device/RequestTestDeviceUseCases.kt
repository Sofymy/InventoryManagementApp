package com.zenitech.imaapp.domain.usecases.request_test_device

import com.zenitech.imaapp.data.repository.RequestTestDeviceRepository

class RequestTestDeviceUseCases(
    val repository: RequestTestDeviceRepository,
    val loadTestDeviceManufacturers: LoadTestDeviceManufacturersUseCase,
    val loadTestDeviceTypes: LoadTestDeviceTypesUseCase,
    val saveTestDeviceRequest: SaveTestDeviceRequestUseCase
)