package com.zenitech.imaapp.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen{
    @Serializable
    object Login

    @Serializable
    object QRReader

    @Serializable
    object DeviceList
}