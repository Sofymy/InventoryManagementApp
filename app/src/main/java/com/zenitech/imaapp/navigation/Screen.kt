package com.zenitech.imaapp.navigation

import androidx.annotation.StringRes
import com.zenitech.imaapp.R
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(
    @StringRes val resourceId: Int
){


    @Serializable
    data object SignIn: Screen(R.string.sign_in)

    @Serializable
    data object QRReader: Screen(R.string.qr_reader)

    @Serializable
    data object Admin: Screen(R.string.admin)

    @Serializable
    data object ManageRequests: Screen(R.string.manage_requests)

    @Serializable
    data object AdminDevices: Screen(R.string.admin)

    @Serializable
    data object AdminDevicesAddDevice: Screen(R.string.admin_add_device)

    @Serializable
    data object AdminDevicesAddDeviceAssets: Screen(R.string.asset_name)

    @Serializable
    data object AdminDevicesAddDeviceSites: Screen(R.string.site)

    @Serializable
    data object AdminDevicesAddDeviceSuccessful: Screen(R.string.successful_device_creation)

    @Serializable
    data object AdminDevicesAddDeviceTypes: Screen(R.string.device_type)

    @Serializable
    data object AdminDevicesAddDeviceManufacturers: Screen(R.string.manufacturer)

    @Serializable
    data class AdminDeviceDetails(val inventoryId: String): Screen(R.string.admin_device_details)

    @Serializable
    data object MyDevices: Screen(R.string.my_devices)

    @Serializable
    data class DeviceDetails(val inventoryId: String) : Screen(R.string.device_details)

    @Serializable
    data class RequestTestDevice(
        val type: String? = null,
        val manufacturer: String? = null,
        val requestDate: String? = null,
        val returnDate: String? = null,
        val additionalRequests: String? = null
    ) : Screen(R.string.request)


    @Serializable
    data class RequestTestDeviceType(val manufacturer: String? = null): Screen(R.string.request_type)


    @Serializable
    data class RequestTestDeviceManufacturer(val type: String ?= null): Screen(R.string.request_manufacturer)


    @Serializable
    data object RequestTestDeviceSuccessful: Screen(R.string.device_list)
}