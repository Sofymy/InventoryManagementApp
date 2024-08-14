package com.zenitech.imaapp.domain.usecases.admin

import com.zenitech.imaapp.data.repository.AdminRepository
import com.zenitech.imaapp.domain.usecases.my_devices.LoadMyDevicesUseCase

class AdminUseCases(
    val repository: AdminRepository,
    val loadAdminDevices: LoadAdminDevicesUseCase,
    val loadAdminDeviceAddDeviceAssets: LoadAdminAddDeviceAssetsUseCase,
    val loadAdminAddDeviceTypes: LoadAdminAddDeviceTypesUseCase,
    val loadAdminAddDeviceManufacturers: LoadAdminAddDeviceManufacturersUseCase,
    val loadAdminAddDevicesSites: LoadAdminAddDeviceSitesUseCase,
    val createDevice: AdminCreateDeviceUseCase
)