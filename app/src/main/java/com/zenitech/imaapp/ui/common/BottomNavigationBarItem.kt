package com.zenitech.imaapp.ui.common

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddComment
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.outlined.AddComment
import androidx.compose.material.icons.outlined.AdminPanelSettings
import androidx.compose.material.icons.outlined.Devices
import androidx.compose.material.icons.outlined.QrCode
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
    val selectedIcon: @Contextual ImageVector,
    val unselectedIcon: @Contextual ImageVector
) {
    @Serializable
    data object MyDevices : BottomNavigationBarItem(
        screen = Screen.MyDevices,
        resourceId = Screen.MyDevices.resourceId,
        unselectedIcon = Icons.TwoTone.Devices,
        selectedIcon = Icons.Filled.Devices
    )

    @Serializable
    data object QRReader : BottomNavigationBarItem(
        screen = Screen.QRReader,
        resourceId = Screen.QRReader.resourceId,
        unselectedIcon = Icons.TwoTone.QrCode,
        selectedIcon = Icons.Filled.QrCode
    )

    @Serializable
    data object Request : BottomNavigationBarItem(
        screen = Screen.RequestTestDevice(),
        resourceId = Screen.RequestTestDevice().resourceId,
        unselectedIcon = Icons.TwoTone.AddComment,
        selectedIcon = Icons.Filled.AddComment
    )

    @Serializable
    data object Admin : BottomNavigationBarItem(
        screen = Screen.Admin,
        resourceId = Screen.Admin.resourceId,
        unselectedIcon = Icons.TwoTone.AdminPanelSettings,
        selectedIcon = Icons.Filled.AdminPanelSettings
    )
}