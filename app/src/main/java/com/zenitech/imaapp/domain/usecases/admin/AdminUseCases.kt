package com.zenitech.imaapp.domain.usecases.admin

import com.zenitech.imaapp.data.repository.AdminRepository
import com.zenitech.imaapp.domain.usecases.admin.devices.AdminCreateDeviceUseCase
import com.zenitech.imaapp.domain.usecases.admin.devices.LoadAdminAddDeviceAssetsUseCase
import com.zenitech.imaapp.domain.usecases.admin.devices.LoadAdminAddDeviceManufacturersUseCase
import com.zenitech.imaapp.domain.usecases.admin.devices.LoadAdminAddDeviceSitesUseCase
import com.zenitech.imaapp.domain.usecases.admin.devices.LoadAdminAddDeviceTypesUseCase
import com.zenitech.imaapp.domain.usecases.admin.devices.LoadAdminDevicesUseCase
import com.zenitech.imaapp.domain.usecases.admin.manage_requests.ApproveRequestUseCase
import com.zenitech.imaapp.domain.usecases.admin.manage_requests.LoadRequestsUseCase
import com.zenitech.imaapp.domain.usecases.admin.manage_requests.RejectRequestUseCase

class AdminUseCases(
    val repository: AdminRepository,
    val loadAdminDevices: LoadAdminDevicesUseCase,
    val loadAdminDeviceAddDeviceAssets: LoadAdminAddDeviceAssetsUseCase,
    val loadAdminAddDeviceTypes: LoadAdminAddDeviceTypesUseCase,
    val loadAdminAddDeviceManufacturers: LoadAdminAddDeviceManufacturersUseCase,
    val loadAdminAddDevicesSites: LoadAdminAddDeviceSitesUseCase,
    val loadRequests: LoadRequestsUseCase,
    val rejectRequestUseCase: RejectRequestUseCase,
    val approveRequestUseCase: ApproveRequestUseCase,
    val createDevice: AdminCreateDeviceUseCase
)