package com.zenitech.imaapp.navigation

import androidx.annotation.StringRes
import com.zenitech.imaapp.R
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(
    @StringRes val resourceId: Int
){
    @Serializable
    data object Login: Screen(R.string.login)

    @Serializable
    data object QRReader: Screen(R.string.qrreader)

    @Serializable
    data object Admin: Screen(R.string.admin)

    @Serializable
    data object MyDevices: Screen(R.string.mydevices)

    @Serializable
    data object DeviceList: Screen(R.string.devicelist)

    @Serializable
    data object Request: Screen(R.string.request)
}