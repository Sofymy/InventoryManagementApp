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
    data object QRReader: Screen(R.string.qrreader)

    @Serializable
    data object Admin: Screen(R.string.admin)

    @Serializable
    data object MyDevices: Screen(R.string.mydevices)

    @Serializable
    data class DeviceDetails(val inventoryNumber: String) : Screen(R.string.devicedetails)

    @Serializable
    data object DeviceList: Screen(R.string.devicelist)

    @Serializable
    data object Request: Screen(R.string.request)
}