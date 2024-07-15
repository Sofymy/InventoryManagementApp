package com.zenitech.imaapp.ui.common

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.AddComment
import androidx.compose.material.icons.twotone.AdminPanelSettings
import androidx.compose.material.icons.twotone.Devices
import androidx.compose.material.icons.twotone.QrCode
import androidx.compose.ui.graphics.vector.ImageVector
import com.zenitech.imaapp.navigation.Screen
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
sealed class BottomNavigationBarItem(
    val screen: Screen,
    @StringRes val resourceId: Int,
    val icon: @Contextual ImageVector
) {
    @Serializable
    data object MyDevices : BottomNavigationBarItem(
        screen = Screen.MyDevices,
        resourceId = Screen.MyDevices.resourceId,
        icon = Icons.TwoTone.Devices
    )

    @Serializable
    data object QRReader : BottomNavigationBarItem(
        screen = Screen.QRReader,
        resourceId = Screen.QRReader.resourceId,
        icon = Icons.TwoTone.QrCode
    )

    @Serializable
    data object Request : BottomNavigationBarItem(
        screen = Screen.Request,
        resourceId = Screen.Request.resourceId,
        icon = Icons.TwoTone.AddComment
    )

    @Serializable
    data object Admin : BottomNavigationBarItem(
        screen = Screen.Admin,
        resourceId = Screen.Admin.resourceId,
        icon = Icons.TwoTone.AdminPanelSettings
    )
}